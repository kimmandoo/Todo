package com.challenge.todo.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.challenge.todo.R
import com.challenge.todo.data.ToDoAdapter
import com.challenge.todo.data.ToDoItem
import com.challenge.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recycler = binding.toDoListRecyclerview

        val adapter = ToDoAdapter()
        val toDoItemList : MutableList<ToDoItem> =
            mutableListOf(ToDoItem(title = "title", content = "content", registerDate = "2024-02-02", dueDate = "2024-02-20", isDone = false))

        adapter.submitList(toDoItemList)
        recycler.adapter = adapter



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.select_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            /** 전체 보기 */
            R.id.view_all -> {

            }
            /** 할일 보기 */
            R.id.view_todo -> {

            }
            /** 완료 목록 보기 */
            R.id.view_done -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}