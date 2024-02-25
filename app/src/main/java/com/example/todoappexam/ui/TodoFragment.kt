package com.example.todoappexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappexam.MyApplication
import com.example.todoappexam.R
import com.example.todoappexam.adapters.TodoAdapter
import com.example.todoappexam.viewmodel.TodoViewModel
import com.example.todoappexam.viewmodel.TodoViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodoFragment : Fragment() {

    private val viewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((activity?.application as MyApplication).repository)
    }

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupAddButton(view)
        observeUiState()
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rvTodos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        todoAdapter = TodoAdapter(listOf())
        recyclerView.adapter = todoAdapter
    }

    private fun setupAddButton(view: View) {
        view.findViewById<Button>(R.id.addButton).setOnClickListener {
            val action = TodoFragmentDirections.actionTodoFragmentToEditTodoFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> showLoading(true)
                    is UiState.Success -> {
                        showLoading(false)
                        todoAdapter.updateData(uiState.data)
                    }
                    is UiState.Error -> {
                        showLoading(false)
                        showError(uiState.exception)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
