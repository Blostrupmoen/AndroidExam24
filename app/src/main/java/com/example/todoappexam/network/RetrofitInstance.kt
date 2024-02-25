package com.example.todoappexam.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Enkel Singleton for å opprette en Retrofit-instans
object RetrofitInstance {
    private const val BASE_URL = "https://dummyapi.online/api/todos"

    // Lazy-initialiserer Retrofit og ApiService
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
