package com.example.pursassessment.view

import com.example.pursassessment.viewmodel.RestaurantViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pursassessment.R
import com.example.pursassessment.ui.theme.backgroundImageModifier
import com.example.pursassessment.ui.theme.gradientBackgroundModifier
import com.example.pursassessment.view.components.BottomMenuLayout
import com.example.pursassessment.view.components.BusinessHoursTag
import com.example.pursassessment.view.components.HeaderText

// Requires Android API level O or higher due to the usage of java.time.LocalDate.
@RequiresApi(Build.VERSION_CODES.O)
// Composable function to set the layout of the restaurant information screen.
@Composable
fun RestaurantInfoScreen(viewModel: RestaurantViewModel) {

    // State to track if the background image should be blurred.
    var isBlurred by remember { mutableStateOf(false) }

    // Observing LiveData from the ViewModel for various restaurant details.
    val locationDetails by viewModel.restaurantData.observeAsState()
    val formattedHours by viewModel.formattedHours.observeAsState()
    val indicatorStatus by viewModel.indicatorStatus.observeAsState()
    val openingStatus by viewModel.openingStatus.observeAsState()

    // Main container for the screen's content.
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "",
            modifier = backgroundImageModifier(isBlurred),
            contentScale = ContentScale.Crop
        )

        Box(modifier = gradientBackgroundModifier())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.TopStart),
            horizontalAlignment = Alignment.Start

        ) {
            HeaderText(restaurantName = locationDetails?.locationName)

            Spacer(modifier = Modifier.padding(10.dp))

            // Conditional rendering of BusinessHoursTag based on the availability of data.
            if (indicatorStatus != null && formattedHours != null && openingStatus != null) {
                BusinessHoursTag(
                    onClick = { isBlurred = !isBlurred },
                    indicatorStatus!!,
                    formattedHours!!,
                    openingStatus!!
                )
            }
        }

        // Conditional rendering of the bottom menu layout.
        if (!isBlurred) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                BottomMenuLayout()
            }
        }
    }
}







