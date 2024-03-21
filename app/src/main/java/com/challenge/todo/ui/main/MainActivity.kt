package com.challenge.todo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.component.TodoAdapter
import data.model.Todo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoAdapter = TodoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity

        initRecyclerView()

        val list = mutableListOf(
            Todo(content = "제목1", title = "내용1"),
            Todo(content = "제목2", title = "내용2"),
            Todo(content = "제목3", title = "내용3"),
            Todo(content = "제목4", title = "내용4"),
            Todo(content = "제목5", title = "내용5"),
            Todo(content = "제목6", title = "내용6"),
        )

        todoAdapter.submitList(list)

    }

    private fun initRecyclerView() {
        binding.rvTodoList.adapter = todoAdapter
    }


}