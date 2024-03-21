package com.challenge.todo.ui.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.databinding.ItemTodoBinding
import data.model.Todo

class TodoAdapter : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TodoViewHolder(ItemTodoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.tvItemTitle.text = todo.title
            binding.tvItemContent.text = todo.content
        }
    }
}

class TodoDiffUtil : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}