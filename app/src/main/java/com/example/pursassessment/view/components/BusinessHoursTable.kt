package com.example.pursassessment.view.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pursassessment.ui.theme.operationDaysStyle
import com.example.pursassessment.ui.theme.operationDaysStyleBold
import com.example.pursassessment.ui.theme.operationHoursStyle
import com.example.pursassessment.ui.theme.operationHoursStyleBold
import java.time.LocalDate

// Requires Android API level O or higher due to the usage of java.time.LocalDate.
@RequiresApi(Build.VERSION_CODES.O)
// Composable function to display business hours in a table format
@Composable
fun BusinessHoursTable(businessHours: Map<String, String>) {
    // Convert the current day to lowercase except first Letter to match format
    val currentDay = LocalDate.now().dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }

    Column(modifier = Modifier.padding(top = 8.dp)) {

        // Iterate over each entry in the businessHours map.
        businessHours.forEach { (day, hoursString) ->

            val hoursList = hoursString.split(", ")

            // For each businessHours map entry, iterate over each hours entry.
            hoursList.forEachIndexed { index, hours ->

                // Determine the style based on whether it's the current day or not.
                val dayStyle = if (currentDay == day) operationDaysStyleBold else operationDaysStyle
                val hoursStyle =
                    if (currentDay == day) operationHoursStyleBold else operationHoursStyle

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {

                    if (index == 0) {
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            style = dayStyle
                        )
                    } else {
                        // Empty space for subsequent time slots.
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Text(
                        text = hours,
                        modifier = Modifier.weight(1f),
                        style = hoursStyle
                    )
                }
            }
        }
    }
}
