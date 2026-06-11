package com.example.carlogmaintenanceapp

import org.junit.Assert.assertEquals
import org.junit.Test

class MaintenanceUtilsTest {


    @Test
    fun testFormat() {
        val mileage = 150000
        val result = MaintenanceUtils.formatMileage(mileage)
        assertEquals("150 000 км", result)
    }
}