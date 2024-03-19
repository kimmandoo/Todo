package com.challenge.todo.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.databinding.ItemTodoMainBinding

class TodoViewHolder(private val binding: ItemTodoMainBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String){
        binding.apply {
            itemMainTv.text = item
        }
    }
}