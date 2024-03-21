package com.challenge.todo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challenge.todo.R
import com.challenge.todo.data.datasource.TodoDatabaseInstance
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.base.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val todoAdapter by lazy { TodoAdapter() }
    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        val todoDBInstance = TodoDatabaseInstance.getDatabase(context = applicationContext)
        binding.apply {
            adapter = todoAdapter
            viewModel.getListFromRoomDB(todoDao = todoDBInstance.todoDao())
            lifecycleOwner?.let { lifecycleOwner ->
                viewModel.todoList.observe(lifecycleOwner) { todoList ->
                    adapter?.submitList(todoList)
                }
            }

            mainFab.setOnClickListener {
                viewModel.insertTodoItem(
                    todoDao = todoDBInstance.todoDao(),
                    Todo("title", "content")
                )
            }
        }
    }
}