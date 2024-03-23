package com.challenge.todo.data

import android.content.ClipData.Item
import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.R
import com.challenge.todo.databinding.TodoItemBinding

class ToDoAdapter(private var toDoList : MutableList<ToDoItem>,
                  val onClickCheckButton : (toDoItem : ToDoItem) -> Unit,
                  val menuInflater: MenuInflater,
                  val context: Context
)
    : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(val binding: TodoItemBinding,
                         val menuInflater: MenuInflater,
                         val itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(binding.root),
        View.OnCreateContextMenuListener {
        fun bindInfo(item: ToDoItem) {
            binding.apply {
                todoIndex.text = position.toString()
                todoTitle.text = item.title
                todoContent.text = item.content
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.delete_cocntext_menu, menu)
            menu?.findItem(R.id.todo_delete)?.setOnMenuItemClickListener {
                val position = adapterPosition
                if( position != RecyclerView.NO_POSITION){
                    itemClickListener.deleteItem(position)
                }

                true
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

    fun setData(newData : MutableList<ToDoItem>){
        toDoList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int,
                                    ): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = TodoItemBinding.inflate(inflater, parent,false)


        return ToDoViewHolder(view,menuInflater,itemClickListener)
    }


    override fun getItemCount(): Int = toDoList.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bindInfo(toDoList[position])

        holder.binding.floatingActionButton1.apply {
            setOnClickListener {
                onClickCheckButton.invoke(toDoList[position])
            }
            check(true)
        }
    }

    lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun OnClick(view: View, position: Int)
        fun deleteItem(position: Int)
    }

}