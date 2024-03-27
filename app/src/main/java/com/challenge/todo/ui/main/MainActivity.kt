package com.challenge.todo.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.component.TodoAdapter
import com.challenge.todo.ui.todo_add.AddActivity
import data.AppDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoAdapter = TodoAdapter()
    private val viewModel: MainViewModel by viewModels()
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        initRecyclerView()

        observeTodos()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

//        viewModel.getAllTodos(db.todoDao())

    }

    private fun initRecyclerView() {
        binding.rvTodoList.adapter = todoAdapter
    }

    private fun observeTodos() {
        viewModel.todoList.observe(this) {
            todoAdapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTodos(db.todoDao())
    }


}