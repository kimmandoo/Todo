package com.challenge.todo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val todoAdapter by lazy { TodoAdapter() }
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.lifecycleOwner = this
            it.adapter = todoAdapter
        }
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            adapter?.submitList(
                arrayListOf(
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa"
                )
            )

        }
    }
}