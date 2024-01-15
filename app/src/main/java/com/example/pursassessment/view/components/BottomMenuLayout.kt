package com.example.pursassessment.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pursassessment.R
import com.example.pursassessment.ui.theme.viewMenuStyle

// Composable function for creating a bottom menu layout.
// Contains two chevron icons and a text in a column
@Composable
fun BottomMenuLayout() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = "",
            tint = Color.Gray
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = "",
            tint = Color.White
        )
    }

    Text(
        text = stringResource(id = R.string.view_menu),
        style = viewMenuStyle
    )
}