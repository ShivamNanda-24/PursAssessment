package com.example.pursassessment.repository

import android.util.Log
import com.example.pursassessment.model.Restaurant
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Interface for Retrofit API calls.
interface ApiService {
    @GET("location.json")
    suspend fun getRestaurantData(): Response<Restaurant>
}

// Singleton object to provide a Retrofit instance.
object RetrofitInstance {
    // Lazy initialization of the ApiService meant to
    // be created only when it's first accessed.
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://purs-demo-bucket-test.s3.us-west-2.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
// Repository class that handles the data operations for restaurants.
class RestaurantRepository(private val apiService: ApiService) {
    // A suspend function to get restaurant data. It returns a Restaurant object.
    suspend fun getRestaurantData(): Restaurant? {
        return try {
            val response = apiService.getRestaurantData()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("RestaurantRepository", "Error: API response was unsuccessful. ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Exception occurred during API call: ${e.message}", e)
            null
        }
    }
}

