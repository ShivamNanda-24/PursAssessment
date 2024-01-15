package com.example.pursassessment

import com.example.pursassessment.model.Hour
import com.example.pursassessment.model.RestaurantDataHelper
import org.junit.Test
import org.junit.Assert.assertEquals

class RestaurantDataHelperTest {

    @Test
    fun testFormattedHours() {
        val hours = listOf(
            Hour("WED", "07:00:00", "13:00:00"),
            Hour("WED", "15:00:00", "22:00:00"),
            Hour("SAT", "10:00:00", "24:00:00"),
            Hour("SUN", "00:00:00", "02:00:00"),
            Hour("SUN", "10:30:00", "21:00:00"),
            Hour("TUE", "07:00:00", "13:00:00"),
            Hour("TUE", "15:00:00", "22:00:00"),
            Hour("THU", "00:00:00", "24:00:00"),
            Hour("FRI", "07:00:00", "24:00:00")
        )

        val formattedHours = RestaurantDataHelper.getFormattedHours(hours)

        // Test expected formatted hours for specific days
        assertEquals("7 AM - 1 PM, 3 PM - 10 PM", formattedHours["Wednesday"])
        assertEquals("7 AM - 1 PM, 3 PM - 10 PM", formattedHours["Tuesday"])
        assertEquals("Closed", formattedHours["Monday"])
        assertEquals("Open 24hrs", formattedHours["Thursday"])
        assertEquals("7 AM - 12 AM", formattedHours["Friday"])
        assertEquals("10 AM - 2 AM", formattedHours["Saturday"])
        assertEquals("10:30 AM - 9 PM", formattedHours["Sunday"])
    }

    @Test
    fun testStatus() {
        val hours = listOf(
            Hour("WED", "07:00:00", "13:00:00"),
            Hour("WED", "15:00:00", "22:00:00"),
            Hour("SAT", "10:00:00", "24:00:00"),
            Hour("SUN", "00:00:00", "02:00:00"),
            Hour("SUN", "10:30:00", "21:00:00"),
            Hour("TUE", "07:00:00", "13:00:00"),
            Hour("TUE", "15:00:00", "22:00:00"),
            Hour("THU", "00:00:00", "24:00:00"),
            Hour("FRI", "07:00:00", "24:00:00")
        )

        // Test status for specific times
        assertEquals("Red", RestaurantDataHelper.getStatus(hours))
        assertEquals("Red", RestaurantDataHelper.getStatus(listOf()))
        assertEquals("Red", RestaurantDataHelper.getStatus(listOf(Hour("MON", "00:00:00", "02:00:00"))))
        assertEquals("Red", RestaurantDataHelper.getStatus(listOf(Hour("WED", "23:00:00", "00:30:00"))))
    }

    @Test
    fun testNextOpenStatus() {
        val hours = listOf(
            Hour("WED", "07:00:00", "13:00:00"),
            Hour("WED", "15:00:00", "22:00:00"),
            Hour("SAT", "10:00:00", "24:00:00"),
            Hour("SUN", "00:00:00", "02:00:00"),
            Hour("SUN", "10:30:00", "21:00:00"),
            Hour("TUE", "07:00:00", "13:00:00"),
            Hour("TUE", "15:00:00", "22:00:00"),
            Hour("THU", "00:00:00", "24:00:00"),
            Hour("FRI", "07:00:00", "24:00:00")
        )

        // Test next open status for specific times and days
        assertEquals("Currently Closed", RestaurantDataHelper.getNextOpenStatus(listOf()))
        assertEquals("Opens again at 7 AM", RestaurantDataHelper.getNextOpenStatus(hours))
        assertEquals("Opens Sunday at 12 AM", RestaurantDataHelper.getNextOpenStatus(listOf(Hour("SUN", "00:00:00", "02:00:00"))))
        assertEquals("Opens Sunday at 10:30 AM", RestaurantDataHelper.getNextOpenStatus(listOf(Hour("SUN", "10:30:00", "21:00:00"))))
        assertEquals("Opens again at 11 PM", RestaurantDataHelper.getNextOpenStatus(listOf(Hour("TUE", "23:00:00", "00:30:00"))))
    }
}
