package com.example.todoappexam.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Definerer en entitet kalt "todoitem" som vil ha en tilsvarende tabell i databasen
@Entity(tableName = "todoitem")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) // Angir at id skal autogenereres
    val id: Int = 0, // Endret til Int for autogenerering
    val title: String,
    val priority: Int,
    val isCompleted: Boolean
    // Du kan legge til flere felt her om nødvendig, som f.eks. dueDate, description, etc.
)
