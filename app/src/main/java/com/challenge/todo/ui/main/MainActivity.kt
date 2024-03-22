package com.challenge.todo.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.component.TodoAdapter
import data.AppDatabase
import data.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoAdapter = TodoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity

        initRecyclerView()

        val list = mutableListOf(
            Todo(title = "제목1", content = "내용1"),
            Todo(title = "제목2", content = "내용2"),
            Todo(title = "제목3", content = "내용3"),
            Todo(title = "제목4", content = "내용4"),
            Todo(title = "제목5", content = "내용5"),
            Todo(title = "제목6", content = "내용6"),
        )

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()


        lifecycleScope.launch(Dispatchers.IO) {
            val todoDao = db.todoDao()
            todoDao.insert(Todo(title = "테스트", content = "이에요"))
            Log.d("룸DB", "${todoDao.getAll()}")
        }


        todoAdapter.submitList(list)

    }

    private fun initRecyclerView() {
        binding.rvTodoList.adapter = todoAdapter
    }


}