package com.example.todoappexam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoappexam.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Setter innholdet til layout-filen 'activity_main.xml'
    }
}
