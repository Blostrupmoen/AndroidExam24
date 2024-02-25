package com.example.todoappexam.database

import androidx.room.*

import com.example.todoappexam.model.TodoItem



@Dao
interface TodoDao {
    @Insert
    fun insertTodo(todoItem: TodoItem): Long

    @Delete
    fun deleteTodo(todoItem: TodoItem): Int

    @Update
    fun updateTodo(todoItem: TodoItem): Int

    @Query("SELECT * FROM TodoItem")
    fun getAllTodos(): List<TodoItem>
}
