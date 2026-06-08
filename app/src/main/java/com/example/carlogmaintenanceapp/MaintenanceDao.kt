package com.example.carlogmaintenanceapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {

    // Вземане на всички записи
    @Query("SELECT * FROM maintenance_logs ORDER BY date DESC")
    fun getAllLogs(): Flow<List<MaintenanceLog>>

    // Добавяне или обновяване
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: MaintenanceLog)

    // Изтриване на запис
    @Delete
    suspend fun deleteLog(log: MaintenanceLog)

    // Вземане по ID
    @Query("SELECT * FROM maintenance_logs WHERE id = :logId LIMIT 1")
    suspend fun getLogById(logId: Int): MaintenanceLog?
}