package com.challenge.todo.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<B : ViewDataBinding, VM: ViewModel>(
    @LayoutRes private val layoutId: Int,
) : AppCompatActivity() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        initView()
    }

    protected abstract fun initView()
}