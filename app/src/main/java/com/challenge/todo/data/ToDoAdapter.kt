package com.challenge.todo.data

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.R
import com.challenge.todo.databinding.TodoItemBinding

private const val TAG = "ToDoAdapter"
class ToDoAdapter(var toDoList: MutableList<ToDoItem>,
                  val onClickCheckButton: (toDoItem: ToDoItem) -> Unit,
                  val menuInflater: MenuInflater
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(val binding: TodoItemBinding,
                         val menuInflater: MenuInflater,
                         val itemClickListener: ItemClickListener?) :
        RecyclerView.ViewHolder(binding.root),
        View.OnCreateContextMenuListener {

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

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
                if (position != RecyclerView.NO_POSITION) {
                    Log.d(TAG, "onCreateContextMenu: ${position}")
                    Log.d(TAG, "itemClickListener: ${itemClickListener}")
                    itemClickListener?.deleteItem(position)
                }
                true
            }
        }

        fun getItemPosition(): Int { // position 프로퍼티의 getter 이름 변경
            return adapterPosition
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

    fun setData(newData: List<ToDoItem>) { // List<ToDoItem>로 수정
        toDoList.clear()
        toDoList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = TodoItemBinding.inflate(inflater, parent, false)
        if(::itemClickListener.isInitialized) return ToDoViewHolder(view, menuInflater, itemClickListener)
        return ToDoViewHolder(view, menuInflater, null)
    }

    override fun getItemCount(): Int = toDoList.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.getItemPosition()// 위치 설정
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