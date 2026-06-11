package com.example.carlogmaintenanceapp

import kotlinx.coroutines.flow.Flow


class MaintenanceRepository(private val maintenanceDao: MaintenanceDao) {

    // вземаме всички записи от DAO
    val allLogs: Flow<List<MaintenanceLog>> = maintenanceDao.getAllLogs()

    // функция за добавяне
    suspend fun insert(log: MaintenanceLog) {
        maintenanceDao.insertLog(log)
    }

    // функция за изтриване
    suspend fun delete(log: MaintenanceLog) {
        maintenanceDao.deleteLog(log)
    }

    // функция за търсене по ID
    suspend fun getLogById(id: Int): MaintenanceLog? {
        return maintenanceDao.getLogById(id)
    }
}