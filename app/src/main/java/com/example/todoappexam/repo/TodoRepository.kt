package com.example.todoappexam.repo

import android.content.Context
import androidx.lifecycle.liveData
import com.example.todoappexam.database.TodoDao
import com.example.todoappexam.model.TodoItem
import com.example.todoappexam.network.ApiHelper
import com.example.todoappexam.network.NetworkUtils
import com.example.todoappexam.utils.Result

class TodoRepository(private val apiHelper: ApiHelper, private val todoDao: TodoDao, private val context: Context) {

    suspend fun getTodos(): Result<List<TodoItem>> {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return Result.Error("Ingen nettverkstilkobling tilgjengelig")
        }
        return try {
            val response = apiHelper.getTodos()
            if (response.isSuccessful) {
                response.body()?.let { todos ->
                    todos.forEach { todoDao.insertTodo(it) }
                    Result.Success(todoDao.getAllTodos())
                } ?: Result.Error("Ingen todos funnet")
            } else {
                Result.Error("Feil ved henting av todos: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Result.Error("Nettverksfeil: ${e.message}")
        }
    }

    suspend fun createTodo(todoItem: TodoItem): Result<TodoItem> {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return Result.Error("Ingen nettverkstilkobling tilgjengelig")
        }
        return try {
            val response = apiHelper.createTodo(todoItem)
            if (response.isSuccessful) {
                response.body()?.let {
                    todoDao.insertTodo(it)
                    Result.Success(it)
                } ?: Result.Error("Feil ved oppretting av todo")
            } else {
                Result.Error("Feil ved oppretting av todo: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Result.Error("Nettverksfeil: ${e.message}")
        }
    }

    fun deleteTodo(todoItem: TodoItem) = liveData {
        emit(Result.Loading)
        try {
            todoDao.deleteTodo(todoItem)
            emit(Result.Success(true)) // Emitting 'true' as a simple success indicator
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    fun updateTodo(todoItem: TodoItem) = liveData {
        emit(Result.Loading)
        try {
            todoDao.updateTodo(todoItem)
            emit(Result.Success(true)) // Similarly, emitting 'true' as a success indicator
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }
}
