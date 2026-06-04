package com.example.carlogmaintenanceapp

import kotlinx.coroutines.flow.Flow

class MaintenanceRepository(private val maintenanceDao: MaintenanceDao) {

    val allLogs: Flow<List<MaintenanceLog>> = maintenanceDao.getAllLogs()

    suspend fun insert(log: MaintenanceLog) {
        maintenanceDao.insertLog(log)
    }

    suspend fun delete(log: MaintenanceLog) {
        maintenanceDao.deleteLog(log)
    }

    suspend fun getLogById(id: Int): MaintenanceLog? {
        return maintenanceDao.getLogById(id)
    }
}