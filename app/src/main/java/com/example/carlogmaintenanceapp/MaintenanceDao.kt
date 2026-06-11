package com.example.carlogmaintenanceapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {

    // вземаме всички записи по дата
    @Query("SELECT * FROM maintenance_logs ORDER BY date DESC")
    fun getAllLogs(): Flow<List<MaintenanceLog>>

    // добавяме/обновяваме нов запис
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: MaintenanceLog)

    // изтриване на запис
    @Delete
    suspend fun deleteLog(log: MaintenanceLog)

    // търсим запис по ID
    @Query("SELECT * FROM maintenance_logs WHERE id = :logId LIMIT 1")
    suspend fun getLogById(logId: Int): MaintenanceLog?
}