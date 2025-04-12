package com.chrisy.probonocases.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.chrisy.probonocases.data.Case
import com.chrisy.probonocases.data.CaseRepository
import com.chrisy.probonocases.data.CaseStatus
import com.chrisy.probonocases.data.LegalReference
import com.chrisy.probonocases.data.NetworkResult
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CaseViewModel(
    private val repository: CaseRepository
) : ViewModel() {

    val allCases: LiveData<List<Case>> = repository.allCases.asLiveData()

    private val _currentCaseDetail = MutableLiveData<Case?>()
    val currentCaseDetail: LiveData<Case?> = _currentCaseDetail

    private val _legalReferences =
        MutableLiveData<NetworkResult<List<LegalReference>>>(NetworkResult.Loading)
    val legalReferences: LiveData<NetworkResult<List<LegalReference>>> = _legalReferences

    fun getCasesByStatus(status: CaseStatus): LiveData<List<Case>> {
        return repository.getCasesByStatus(status.toString()).asLiveData()
    }

    fun loadCaseDetail(id: Long) {
        viewModelScope.launch {
            val case = repository.getCaseById(id).firstOrNull()
            _currentCaseDetail.value = case
        }
    }

    fun saveCase(case: Case) {
        viewModelScope.launch {
            if (case.id == 0L) {
                repository.insertCase(case)
            } else {
                repository.updateCase(case)
            }
        }
    }

    fun updateCaseStatus(caseId: Long, newStatus: CaseStatus) {
        viewModelScope.launch {
            val currentCase = _currentCaseDetail.value
            if (currentCase != null && currentCase.id == caseId) {
                val updatedCase = currentCase.copy(
                    status = newStatus,
                    lastUpdated = System.currentTimeMillis()
                )
                repository.updateCase(updatedCase)
                _currentCaseDetail.value = updatedCase
            }
        }
    }

    fun searchLegalReferences(query: String) {
        viewModelScope.launch {
            _legalReferences.value = NetworkResult.Loading
            try {
                val result = repository.searchLegalReferences(query).firstOrNull()
                _legalReferences.value = NetworkResult.Success(result ?: emptyList())
            } catch (e: Exception) {
                _legalReferences.value = NetworkResult.Error("Search failed: ${e.message}")
            }
        }
    }

    fun clearLegalReferences() {
        _legalReferences.value = NetworkResult.Success(emptyList())
    }
}