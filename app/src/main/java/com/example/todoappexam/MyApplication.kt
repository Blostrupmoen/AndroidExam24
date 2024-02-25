package com.example.todoappexam

import android.app.Application
import com.example.todoappexam.database.AppDatabase
import com.example.todoappexam.network.ApiHelper
import com.example.todoappexam.network.RetrofitInstance
import com.example.todoappexam.repo.TodoRepository

class MyApplication : Application() {
    lateinit var repository: TodoRepository

    override fun onCreate() {
        super.onCreate()

        // Initialiserer ApiHelper med ApiService fra RetrofitInstance
        val apiHelper = ApiHelper(RetrofitInstance.apiService)

        // Initialiserer Room-databasen og henter TodoDao
        val todoDao = AppDatabase.getDatabase(this).todoDao()

        // Oppdaterer TodoRepository-konstruktøren for å inkludere kontekst
        repository = TodoRepository(apiHelper, todoDao, this) // Passer 'this' for kontekst
    }
}

