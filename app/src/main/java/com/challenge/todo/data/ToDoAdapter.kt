package com.challenge.todo.data

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.R
import com.challenge.todo.data.db.entity.ToDoEntity
import com.challenge.todo.databinding.TodoItemBinding
import com.challenge.todo.ui.todo_detail.TodoDetailDialog

private const val TAG = "ToDoAdapter"
class ToDoAdapter() : ListAdapter<ToDoEntity, ToDoAdapter.ToDoViewHolder>(ToDoComparator()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.content)
    }

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val toDoTitleView : TextView = itemView.findViewById(R.id.todo_title)
        private val toDoContentView : TextView = itemView.findViewById(R.id.todo_content)

        fun bind(title : String?, content : String?){
            toDoTitleView.text = title
            toDoContentView.text = content
        }
        companion object{
            fun create(parent: ViewGroup) : ToDoViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.todo_item,parent,false)
                return ToDoViewHolder(view)
            }
        }

    }

    class ToDoComparator : DiffUtil.ItemCallback<ToDoEntity>(){
        override fun areItemsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
            return oldItem.content == newItem.content
        }

    }
    lateinit var itemClickListner: ItemClickListener
    interface ItemClickListener {
        fun OnClick(view: View, position: Int)
        fun deleteItem(position: Int)
    }
}