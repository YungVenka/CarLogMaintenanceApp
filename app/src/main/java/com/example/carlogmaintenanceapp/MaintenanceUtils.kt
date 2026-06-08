package com.example.carlogmaintenanceapp

import java.util.Locale

object MaintenanceUtils {
    fun formatMileage(mileage: Int): String {
        return String.format(Locale.GERMANY, "%,d км", mileage).replace('.', ' ')
    }

    fun formatPrice(price: Double): String {
        return String.format(Locale.US, "%.2f €", price)
    }
}