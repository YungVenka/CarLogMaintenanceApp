package com.example.carlogmaintenanceapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maintenance_logs")
data class MaintenanceLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val mileage: Int,
    val price: Double,
    val date: Long,
    val imageUri: String? = null
)