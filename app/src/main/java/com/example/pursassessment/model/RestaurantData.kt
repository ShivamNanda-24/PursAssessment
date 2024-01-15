package com.example.pursassessment.model

import com.google.gson.annotations.SerializedName

// Data class representing the format of the restaurant data.
// It includes a name and a list of operating hours.
data class Restaurant(
    @SerializedName("location_name")
    val locationName: String,
    val hours: List<Hour>
)

// Data class representing the format of hours of
// operation of the restaurant
data class Hour(
    @SerializedName("day_of_week")
    val dayOfWeek: String,
    @SerializedName("start_local_time")
    val startLocalTime: String,
    @SerializedName("end_local_time")
    val endLocalTime: String
)
