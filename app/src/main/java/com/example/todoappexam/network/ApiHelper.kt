package com.example.todoappexam.network


import com.example.todoappexam.model.TodoItem
import retrofit2.Response


class ApiHelper(private val apiService: ApiService) {
    suspend fun getTodos(): Response<List<TodoItem>> = apiService.getTodos()
    suspend fun createTodo(todoItem: TodoItem): Response<TodoItem> = apiService.createTodo(todoItem)
}
