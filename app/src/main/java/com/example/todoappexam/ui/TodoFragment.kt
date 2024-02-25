package com.example.todoappexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappexam.R
import com.example.todoappexam.adapters.TodoAdapter
import com.example.todoappexam.viewmodel.TodoViewModel
import com.example.todoappexam.utils.Result

class TodoFragment : androidx.fragment.app.Fragment() {

    private val viewModel: TodoViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view) // Initialiserer RecyclerView
        observeTodos() // Starter observasjon av todos fra ViewModel

        // Legger til OnClickListener for "Legg til ny Todo" knappen
        view.findViewById<Button>(R.id.addButton).setOnClickListener {
            val action = TodoFragmentDirections.actionTodoFragmentToEditTodoFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rvTodos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        todoAdapter = TodoAdapter(listOf()) // Initialiserer adapter med tom liste
        recyclerView.adapter = todoAdapter
    }

    private fun observeTodos() {
        viewModel.todos.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    todoAdapter.updateData(result.data) // Oppdaterer adapter med nye data
                    view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.GONE // Skjuler ProgressBar
                }
                is Result.Error -> {
                    view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.GONE // Skjuler ProgressBar
                    Toast.makeText(context, "Feil: ${result.exception}", Toast.LENGTH_LONG).show() // Viser feilmelding
                }
                Result.Loading -> {
                    view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.VISIBLE // Viser ProgressBar
                }
            }
        }
    }
}
