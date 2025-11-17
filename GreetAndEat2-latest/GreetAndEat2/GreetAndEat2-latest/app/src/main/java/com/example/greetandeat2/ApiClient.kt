package com.example.greetandeat2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

// The ApiClient object is a singleton responsible for creating and managing
// the Retrofit instance used to make API calls in the app.
object ApiClient {
    private const val BASE_URL = "https://progapi.onrender.com"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)// Sets the API base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java) // Creates an implementation of the ApiService interface
    }
}
