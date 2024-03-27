package com.challenge.todo.ui.component

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.databinding.ItemTodoBinding
import data.model.Todo

class TodoAdapter : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffUtil()) {

    interface ItemDeleteListener {
        fun onDelete(position: Int)
    }

    lateinit var itemDeleteListener: ItemDeleteListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TodoViewHolder(ItemTodoBinding.inflate(inflater, parent, false), itemDeleteListener)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(private val binding: ItemTodoBinding, private val itemDeleteListener: ItemDeleteListener) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(todo: Todo) {
            binding.tvItemTitle.text = todo.title
            binding.tvItemContent.text = todo.content
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val menuItem = menu?.add(0, layoutPosition, 0, "삭제2")
            menuItem?.setOnMenuItemClickListener {
                //inner class 를 안하고 할 수 있나?
                itemDeleteListener.onDelete(layoutPosition)
                true
            }
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