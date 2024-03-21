package com.challenge.todo.ui.detail

import androidx.activity.viewModels
import com.challenge.todo.R
import com.challenge.todo.databinding.ActivityDetailBinding
import com.challenge.todo.ui.base.BaseActivity

class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {
    override val viewModel: DetailViewModel by viewModels()
    override fun initView() {
        binding.apply {

        }
    }
}