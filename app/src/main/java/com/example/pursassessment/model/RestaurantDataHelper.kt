package com.example.pursassessment.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// Helper class with static methods to process and format restaurant data as per requirements
class RestaurantDataHelper {

    // A map to assign order to days of the week for sorting.
    companion object {
        private val dayOrder = mapOf(
            "Monday" to 1,
            "Tuesday" to 2,
            "Wednesday" to 3,
            "Thursday" to 4,
            "Friday" to 5,
            "Saturday" to 6,
            "Sunday" to 7
        )

        // Requires Android API level O or higher due to the usage of java.time.LocalDate.
        @RequiresApi(Build.VERSION_CODES.O)

        // Function to get formatted and sorted hours required to display on the UI.
        fun getFormattedHours(hours: List<Hour>): Map<String, String> {
            val formattedHoursMap = dayOrder.keys.associateWith { "Closed" }.toMutableMap()
            val sortedHours = hours.sortedBy { dayOrder[it.dayOfWeek] ?: Int.MAX_VALUE }
            var i = 0

            while (i < sortedHours.size) {
                val currentHour = sortedHours[i]

                // Use formatTime to handle all time formatting
                var formattedHour = formatTime(currentHour.startLocalTime) + " - " + formatTime(currentHour.endLocalTime)

                if (formattedHour =="12 AM - 12 AM"){
                    formattedHour = "Open 24hrs"
                }
                // Handle the edge case for day transition
                if (currentHour.endLocalTime == "24:00:00" && i < sortedHours.size - 1 && sortedHours[i + 1].startLocalTime == "00:00:00") {
                    val nextHour = sortedHours[i + 1]
                    // Use "00:00:00" to represent "24:00:00" for end time
                    formattedHour = formatTime(currentHour.startLocalTime) + " - " + formatTime(nextHour.endLocalTime)
                    i++
                }

                val dayName = convertDayOfWeek(currentHour.dayOfWeek)
                formattedHoursMap[dayName] = if (formattedHoursMap[dayName] == "Closed") {
                    formattedHour
                } else {
                    formattedHoursMap[dayName] + ", " + formattedHour
                }

                i++
            }

            return formattedHoursMap.toSortedMap(compareBy { dayOrder[it] ?: Int.MAX_VALUE })
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun formatTime(timeString: String): String {
            val time = if (timeString == "24:00:00") LocalTime.MIDNIGHT else LocalTime.parse(timeString)
            val formatter = if (time.minute == 0) DateTimeFormatter.ofPattern("h a") else DateTimeFormatter.ofPattern("h:mm a")
            return time.format(formatter.withLocale(Locale.getDefault()))
        }

        // Helper function to convert three-letter day abbreviations to full day names.
        private fun convertDayOfWeek(day: String): String {
            return when (day) {
                "MON" -> "Monday"
                "TUE" -> "Tuesday"
                "WED" -> "Wednesday"
                "THU" -> "Thursday"
                "FRI" -> "Friday"
                "SAT" -> "Saturday"
                "SUN" -> "Sunday"
                else -> day
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        // Function to determine the indicator status based on
        // current time and extracted business hours.
        fun getStatus(hours: List<Hour>): String {
            val currentDateTime = LocalDateTime.now()
            val currentDay = currentDateTime.dayOfWeek.name.substring(0, 3)
            val currentTime = currentDateTime.toLocalTime()

            hours.firstOrNull { it.dayOfWeek.equals(currentDay, ignoreCase = true) }
                ?.let { todayHours ->

                    val openingTime = LocalTime.parse(todayHours.startLocalTime)

                    val closingTime =
                        if (todayHours.endLocalTime == "24:00:00") LocalTime.MIDNIGHT else LocalTime.parse(
                            todayHours.endLocalTime
                        )

                    // Checks and returns the status indicator based on business hours.
                    return when {
                        currentTime.isBefore(openingTime) || currentTime.isAfter(closingTime) -> "Red"
                        currentTime.plusHours(1).isAfter(closingTime) -> "Yellow"
                        else -> "Green"
                    }
                }
            return "Red"
        }

        @RequiresApi(Build.VERSION_CODES.O)
        // Function to get the next opening status of the restaurant.
        fun getNextOpenStatus(hours: List<Hour>): String {

            // Determines the current and next day, and the current time.
            val currentDateTime = LocalDateTime.now()
            val currentDay = currentDateTime.dayOfWeek.name.substring(0, 3)
            val currentTime = currentDateTime.toLocalTime()

            val todayHours = hours.filter { it.dayOfWeek.equals(currentDay, ignoreCase = true) }
            val tomorrowHours = hours.filter { it.dayOfWeek.equals(currentDateTime.plusDays(1).dayOfWeek.name.substring(0, 3), ignoreCase = true) }

            todayHours.sortedBy { LocalTime.parse(it.startLocalTime) }.forEach { hour ->

                val openingTime = LocalTime.parse(hour.startLocalTime)
                val closingTime = if (hour.endLocalTime == "24:00:00") LocalTime.MIDNIGHT else LocalTime.parse(hour.endLocalTime)

                if (currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime)) {
                    return if (hour == todayHours.last()) {
                        "Open until ${formatTime(hour.endLocalTime)}"
                    } else {
                        "Open until ${formatTime(hour.endLocalTime)}, reopens at ${formatTime(todayHours[todayHours.indexOf(hour) + 1].startLocalTime)}"
                    }
                }
                else if (currentTime.isBefore(openingTime)) {
                    return "Opens again at ${formatTime(hour.startLocalTime)}"
                }
            }

            // If no hours are found for the current day or if the current time is past today's hours
            if (tomorrowHours.isNotEmpty()) {
                val nextOpening = tomorrowHours.minByOrNull { LocalTime.parse(it.startLocalTime) }

                nextOpening?.let {
                    val nextOpeningTime = LocalTime.parse(it.startLocalTime)
                    val timeUntilNextOpening = Duration.between(currentTime, nextOpeningTime).plusDays(1)
                    if (timeUntilNextOpening.toHours() < 24) {
                        return "Opens again at ${formatTime(it.startLocalTime)}"
                    }
                }
            }
            // Logic to find the next opening time.
            val nextHour = hours.minByOrNull {
                val dayDifference = (dayOrder[it.dayOfWeek] ?: Int.MAX_VALUE) - (dayOrder[currentDay] ?: Int.MAX_VALUE)

                dayDifference.takeIf { it > 0 } ?: Int.MAX_VALUE
            }
            nextHour?.let {
                return "Opens ${convertDayOfWeek(it.dayOfWeek)} at ${formatTime(it.startLocalTime)}"
            }
            return "Currently Closed"
        }
    }
}
