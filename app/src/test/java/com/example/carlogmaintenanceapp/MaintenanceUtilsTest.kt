package com.example.carlogmaintenanceapp

import org.junit.Assert.assertEquals
import org.junit.Test

class MaintenanceUtilsTest {

    @Test
    fun testFormatMileage() {
        val result = MaintenanceUtils.formatMileage(180000)
        assertEquals("180 000 км", result)
    }

    @Test
    fun testFormatPrice() {
        val result = MaintenanceUtils.formatPrice(120.50)
        assertEquals("120.50 €", result)
    }
}