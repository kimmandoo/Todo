package com.challenge.todo.ui.main

import android.view.ContextMenu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.R
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.data.dto.TodoState
import com.challenge.todo.databinding.ItemTodoMainBinding

private const val TAG = "TodoViewHolder"

class TodoViewHolder(
    private val binding: ItemTodoMainBinding,
    private val onTodoItemClick: (todo: Todo) -> Unit = { _ -> },
    private val onTodoItemDelete: (todo: Todo) -> Unit = { _ -> },
    private val onTodoItemChecked: (todo: Todo, isChecked: Boolean) -> Unit = { _, _ -> }
) :
    RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    lateinit var todo: Todo
    fun bind(item: Todo) {
        todo = item
        binding.apply {
            itemView.setOnClickListener {
                onTodoItemClick(item)
            }

            itemCb.isVisible = item.state != TodoState.DONE.state
            itemCb.setOnClickListener{
                onTodoItemChecked(item, itemCb.isChecked)
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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = MenuInflater(
            binding.root.context
        )
        inflater.inflate(R.menu.menu_context, menu)
        val menuItem = menu?.findItem(R.id.context_menu_remove)
        menuItem?.setOnMenuItemClickListener {
            onTodoItemDelete(todo)
            true
        }

    }
}