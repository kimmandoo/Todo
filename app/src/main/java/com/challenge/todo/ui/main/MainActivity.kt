package com.challenge.todo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.component.TodoAdapter
import data.AppDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoAdapter = TodoAdapter()
    private val viewModel: MainViewModel by viewModels()
    private val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        initRecyclerView()

        observeTodos()

        val todoDao = db.todoDao()

    }

    private fun initRecyclerView() {
        binding.rvTodoList.adapter = todoAdapter
    }

    private fun observeTodos() {
        viewModel.todoList.observe(this) {
            todoAdapter.submitList(it)
        }
    }


}