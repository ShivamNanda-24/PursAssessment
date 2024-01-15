package com.example.pursassessment.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pursassessment.model.Restaurant
import com.example.pursassessment.model.RestaurantDataHelper
import com.example.pursassessment.repository.RestaurantRepository
import com.example.pursassessment.repository.RetrofitInstance
import kotlinx.coroutines.launch

// Requires Android API level O or higher due to the usage of java.time.LocalDate.
@RequiresApi(Build.VERSION_CODES.O)
class RestaurantViewModel : ViewModel() {

    // Instantiating the repository for data operations.
    private val repository = RestaurantRepository(RetrofitInstance.api)

    // MutableLiveData for handling of restaurant related data
    private val _restaurantData = MutableLiveData<Restaurant>()
    private val _formattedHours = MutableLiveData<Map<String, String>>()
    private val _indicatorStatus = MutableLiveData<String>()
    private val _openingStatus = MutableLiveData<String>()
    private val _error = MutableLiveData<String>()

    // LiveData exposed for observing restaurant related data changes.
    val restaurantData: LiveData<Restaurant> = _restaurantData
    val formattedHours: LiveData<Map<String, String>> = _formattedHours
    val indicatorStatus: LiveData<String> = _indicatorStatus
    val openingStatus: LiveData<String> = _openingStatus

    init {
        fetchRestaurantData()
    }

    // Requires Android API level O or higher due to the usage of java.time.LocalDate.
    @RequiresApi(Build.VERSION_CODES.O)
    // Function to fetch restaurant data asynchronously within the ViewModel scope.
    private fun fetchRestaurantData() {

        viewModelScope.launch {
            try {
                // Attempt to retrieve restaurant data from the repository.
                repository.getRestaurantData()?.let { locationData ->
                    // Post the retrieved data to the LiveData if successful.
                    _restaurantData.postValue(locationData)
                    _formattedHours.postValue(RestaurantDataHelper.getFormattedHours(locationData.hours))
                    _indicatorStatus.postValue(RestaurantDataHelper.getStatus(locationData.hours))
                    _openingStatus.postValue(RestaurantDataHelper.getNextOpenStatus(locationData.hours))
                }
                    ?: run {
                    // Post an error message if no data is found.
                    _error.postValue("Error: Data not found")
                    Log.e("fetchRestaurantData", "Error posting restaurant data")
                }
            }
            catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
                Log.e("fetchRestaurantData", "Exception occurred while posting restaurant data : ${e.message}", e)
            }
        }
    }
}
