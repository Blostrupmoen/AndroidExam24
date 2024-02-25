package com.example.todoappexam


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoappexam.ui.TodoFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Oppdater til korrekt R-klassen

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TodoFragment())
                .commitNow()
        }
    }
}
