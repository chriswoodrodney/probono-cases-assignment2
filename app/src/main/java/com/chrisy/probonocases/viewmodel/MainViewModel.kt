package com.chrisy.probonocases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisy.probonocases.data.DatabaseSeeder
import kotlinx.coroutines.launch

class MainViewModel(
    private val databaseSeeder: DatabaseSeeder
) : ViewModel() {

    fun initializeDatabase() {
        viewModelScope.launch {
            // Call suspend function within a coroutine scope
            databaseSeeder.populateSampleData()
        }
    }
}