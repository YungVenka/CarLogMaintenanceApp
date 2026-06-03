package com.example.carlogmaintenanceapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {

    // Връща всички ремонти, подредени от най-новия към най-стария
    @Query("SELECT * FROM maintenance_logs ORDER BY date DESC")
    fun getAllLogs(): Flow<List<MaintenanceLog>>

    // Записва нов ремонт. Ако съвпадне ID (при дублиране), го презаписва
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: MaintenanceLog)

    // Изтрива конкретен ремонт
    @Delete
    suspend fun deleteLog(log: MaintenanceLog)

    // Позволява ни да вземем конкретен ремонт по неговото ID (за детайлния екран)
    @Query("SELECT * FROM maintenance_logs WHERE id = :logId LIMIT 1")
    suspend fun getLogById(logId: Int): MaintenanceLog?
}