package com.example.todoappexam.network

import com.example.todoappexam.model.TodoItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): Response<List<TodoItem>>

    @POST("todos")
    suspend fun createTodo(@Body todoItem: TodoItem): Response<TodoItem>
}
