package com.example.carlogmaintenanceapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MaintenanceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MaintenanceRepository
    val allLogs: androidx.lifecycle.LiveData<List<MaintenanceLog>>

    init {
        val dao = AppDatabase.getDatabase(application).maintenanceDao()
        repository = MaintenanceRepository(dao)
        allLogs = repository.allLogs.asLiveData()
    }

    fun insert(log: MaintenanceLog) = viewModelScope.launch {
        repository.insert(log)
    }

    fun delete(log: MaintenanceLog) = viewModelScope.launch {
        repository.delete(log)
    }
}