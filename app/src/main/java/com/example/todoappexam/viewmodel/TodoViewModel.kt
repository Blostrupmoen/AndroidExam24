package com.example.todoappexam.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.todoappexam.model.TodoItem
import com.example.todoappexam.repo.TodoRepository
import com.example.todoappexam.utils.Result
import kotlinx.coroutines.Dispatchers

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    // LiveData for å holde en liste med todos
    val todos = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val result = repository.getTodos()
            emit(result)
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Ukjent feil"))
        }
    }

    // En funksjon for å legge til en todo
    fun addTodo(todoItem: TodoItem) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val result = repository.createTodo(todoItem)
            emit(result)
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Ukjent feil"))
        }
    }


    // Disse funksjonene er ikke fullt implementert, men jeg lar dem være for nå
    // og de må fullføres med riktig logikk for å slette og oppdatere todos
    fun deleteTodo(todoItem: TodoItem) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val result = repository.deleteTodo(todoItem)
            emit(result)
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Ukjent feil"))
        }
    }


    fun updateTodo(todoItem: TodoItem) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val result = repository.updateTodo(todoItem)
            emit(result)
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Ukjent feil"))
        }
    }

}