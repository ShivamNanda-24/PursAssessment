package com.example.pursassessment.view.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pursassessment.R
import com.example.pursassessment.ui.theme.businessHoursBoxModifier
import com.example.pursassessment.ui.theme.descriptionStyle
import com.example.pursassessment.ui.theme.operationalHoursStyle

// Requires Android API level O or higher due to the usage of java.time.LocalDate.
@RequiresApi(Build.VERSION_CODES.O)
// Composable function to display a business hours tag with expandable functionality.
@Composable
fun BusinessHoursTag(
    onClick: () -> Unit,
    indicatorStatus: String,
    formattedHours: Map<String, String>,
    openingStatus: String
) {
    // State for tracking if the tag is expanded
    var isExpanded by remember { mutableStateOf(false) }

    // Determines the color of the status indicator based on the provided status.
    val statusColor = when (indicatorStatus) {
        "Red" -> Color.Red
        "Green" -> Color.Green
        "Yellow" -> Color.Yellow
        else -> Color.Gray
    }

    Box(
        modifier = businessHoursBoxModifier {
            onClick()
            isExpanded = !isExpanded // Toggle the expanded state on click.
        }
    ) {
        // Row layout for arranging elements horizontally.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                modifier = Modifier.weight(1f)

            ) {
                // Row layout for the opening status and indicator.
                Row(
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = openingStatus,
                        style = operationalHoursStyle

                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(statusColor, shape = CircleShape)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Icon component for the expand/collapse indicator.
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(id = R.string.hours_tag),
                        tint = Color.Black,
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(top = 10.dp)
                    )
                }

                Text(
                    text = stringResource(id = R.string.hours_tag),
                    style = descriptionStyle
                )

                // Conditional layout for displaying business hours when expanded.
                if (isExpanded) {
                    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(top = 13.dp))
                    BusinessHoursTable(businessHours = formattedHours)
                }
            }
        }
    }
}