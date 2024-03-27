package com.challenge.todo.ui.todo_add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityAddBinding
import data.AppDatabase
import data.model.Todo

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }
    private val viewModel: AddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        binding.activity = this

        binding.btnAdd.setOnClickListener {
            val title = binding.tietTitle.text.toString()
            val content = binding.tietContent.text.toString()
            viewModel.addTodo(db.todoDao(), Todo(title = title, content = content))
            finish()
        }

    }


}