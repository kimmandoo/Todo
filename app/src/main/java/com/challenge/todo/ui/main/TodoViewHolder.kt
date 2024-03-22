package com.challenge.todo.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.databinding.ItemTodoMainBinding

class TodoViewHolder(
    private val binding: ItemTodoMainBinding,
    private val onTodoItemClick: (todo: Todo) -> Unit = { _ -> }
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Todo) {
        binding.apply {
            itemView.setOnClickListener {
                onTodoItemClick(item)
            }

            with(item) {
                itemTvTitle.apply {
                    text = title
                }
                itemTvContent.apply {
                    if (content.isNullOrEmpty()) visibility = View.GONE
                    else text = content
                }
            }
        }
    }
}