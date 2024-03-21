package com.challenge.todo.data

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.databinding.TodoItemBinding

class ToDoAdapter() : ListAdapter<ToDoItem, ToDoAdapter.ToDoViewHolder>(ToDoComporator()){

    class ToDoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(item : ToDoItem){
            binding.apply {
                todoIndex.text = position.toString()
                todoTitle.text = item.title
                todoContent.text = item.content

                with(item){

                }
            }
        }
    }

    class ToDoComporator : DiffUtil.ItemCallback<ToDoItem>() {
        override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent,false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bindInfo(currentList[position])
    }


}