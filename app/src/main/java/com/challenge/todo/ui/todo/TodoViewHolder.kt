package com.challenge.todo.ui.todo

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.challenge.todo.databinding.ItemTodoMainBinding

class TodoViewHolder(private val binding: ItemTodoMainBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String){
        binding.apply {
            itemMainTv.text = item
        }
    }
}