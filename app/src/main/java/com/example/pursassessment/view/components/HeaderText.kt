package com.example.pursassessment.view.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.pursassessment.ui.theme.restaurantNameTextStyle

// Composable function to display the name of restaurant as a header.
@Composable
fun HeaderText(restaurantName: String?) {
    Text(
        text = (restaurantName ?: "" ),
        style = restaurantNameTextStyle
    )
}