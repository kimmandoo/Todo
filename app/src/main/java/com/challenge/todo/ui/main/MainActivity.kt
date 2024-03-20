package com.challenge.todo.ui.main

import android.app.Application
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.challenge.todo.R
import com.challenge.todo.data.datasource.TodoDatabase
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.data.dto.TodoState
import com.challenge.todo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val todoAdapter by lazy { TodoAdapter() }
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.lifecycleOwner = this
            it.adapter = todoAdapter
        }
    }

    companion object : Application() {
        val db = Room.databaseBuilder(applicationContext, TodoDatabase::class.java, "todo").build()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            adapter?.submitList(
                arrayListOf(
                    Todo("Title", "content","" ,TodoState.TODO),
                    Todo("Title", null,"", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                    Todo("Title", "content","", TodoState.TODO),
                )
            )

        }
    }
}