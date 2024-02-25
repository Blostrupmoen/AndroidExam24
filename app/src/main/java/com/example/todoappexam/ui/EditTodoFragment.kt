package com.example.todoappexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoappexam.MyApplication
import com.example.todoappexam.R
import com.example.todoappexam.model.TodoItem
import com.example.todoappexam.viewmodel.TodoViewModel
import com.example.todoappexam.viewmodel.TodoViewModelFactory

class EditTodoFragment : Fragment() {

    private val viewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((activity?.application as MyApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoTitleEditText = view.findViewById<EditText>(R.id.todoTitleEditText)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        val todoPriority = 1 // Anta at du har logikk for å hente prioritet

        saveButton.setOnClickListener {
            val todoTitle = todoTitleEditText.text.toString()

            if (todoTitle.isNotEmpty()) {
                val todoItem = TodoItem(title = todoTitle, priority = todoPriority, isCompleted = false)
                viewModel.addTodo(todoItem)
                findNavController().popBackStack()
            } else {
                // Vis feilmelding
            }
        }
    }
}
