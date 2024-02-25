package com.example.todoappexam.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.todoappexam.model.TodoItem

// Definer databasen med Room-annotasjoner, inkluderer alle entiteter og setter versjonen
@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Definer en abstrakt funksjon for hver DAO som appen bruker
    abstract fun todoDao(): TodoDao

    // Singleton for å hindre flere instanser av databasen å åpnes samtidig
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Hjelpefunksjon for å få en instans av databasen
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_database" // Databasenavn
                ).fallbackToDestructiveMigration() // Strategi for migrasjon mellom versjoner
                    .build()
                INSTANCE = instance
                // Returnere instansen
                instance
            }
        }
    }
}
