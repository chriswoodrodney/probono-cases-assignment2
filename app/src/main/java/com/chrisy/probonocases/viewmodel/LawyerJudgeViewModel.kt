package com.chrisy.probonocases.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisy.probonocases.data.Judge
import com.chrisy.probonocases.data.Lawyer
import com.chrisy.probonocases.data.LawyerJudgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LawyerJudgeViewModel(
    private val repository: LawyerJudgeRepository
) : ViewModel() {

    // These are now consistent and use Flow
    val allLawyers: LiveData<List<Lawyer>> = repository.allLawyers
    val allJudges: Flow<List<Judge>> = repository.allJudges

    private val _selectedLawyer = MutableLiveData<Lawyer?>()
    val selectedLawyer: LiveData<Lawyer?> = _selectedLawyer

    private val _selectedJudge = MutableLiveData<Judge?>()
    val selectedJudge: LiveData<Judge?> = _selectedJudge

    fun loadLawyerDetail(id: Long) {
        viewModelScope.launch {
            // Using collect to gather the result from Flow
            repository.getLawyerById(id).collect { lawyer: Lawyer? ->  // Explicitly specify the type
                _selectedLawyer.value = lawyer
            }
        }
    }


    // Load judge detail using Flow, converting to LiveData for observation
    fun loadJudgeDetail(id: Long) {
        viewModelScope.launch {
            // Collect first value from Flow to get the judge detail
            val judge = repository.getJudgeById(id).first()
            _selectedJudge.value = judge
        }
    }

    // Save lawyer (insert or update based on id)
    fun saveLawyer(lawyer: Lawyer) {
        viewModelScope.launch {
            if (lawyer.id == 0L) {
                repository.insertLawyer(lawyer)
            } else {
                repository.updateLawyer(lawyer)
            }
        }
    }

    // Save judge (insert or update based on id)
    fun saveJudge(judge: Judge) {
        viewModelScope.launch {
            if (judge.id == 0L) {
                repository.insertJudge(judge)
            } else {
                repository.updateJudge(judge)
            }
        }
    }

    // Delete lawyer
    fun deleteLawyer(lawyer: Lawyer) {
        viewModelScope.launch {
            repository.deleteLawyer(lawyer)
        }
    }

    // Delete judge
    fun deleteJudge(judge: Judge) {
        viewModelScope.launch {
            repository.deleteJudge(judge)
        }
    }
}

private fun Any.collect(any: Any) {

}
