package com.challenge.todo.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityDetailBinding
import com.challenge.todo.ui.main.MainActivity

class DetailActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail).also {
            it.lifecycleOwner = this
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

        }

    }
}