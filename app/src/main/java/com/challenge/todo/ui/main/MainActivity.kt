package com.challenge.todo.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.challenge.todo.R
import com.challenge.todo.data.ToDoAdapter
import com.challenge.todo.data.ToDoItem
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.todo_detail.TodoDetailDialog
import java.time.LocalDateTime

private const val TAG = "MainActivity";
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()
    private val todoList = arrayListOf<ToDoItem>(ToDoItem(title = "title", content = "content", registerDate = "2024-02-02", dueDate = "2024-02-20", isDone = false))

    private val recycler: RecyclerView by lazy {
        binding.toDoListRecyclerview
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ToDoAdapter(todoList, onClickCheckButton = {
            viewModel.checkButton(it)
        } )

        val toDoItemList : MutableList<ToDoItem> =
            mutableListOf(ToDoItem(title = "title", content = "content", registerDate = "2024-02-02", dueDate = "2024-02-20", isDone = false))

        Log.d(TAG, "onCreate: $toDoItemList")

        viewModel._toDoList.observe(this, Observer {
            (binding.toDoListRecyclerview.adapter as ToDoAdapter).setData(it)
        })

//        val dataObserver: Observer<List<ToDoItem>> = Observer {
//            items.value = it
//            val adapter = ToDoAdapter()
//            recycler.adapter = adapter
//        }

//        viewModel.toDoList.observe(this, dataObserver)

//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.createTodoBtn.setOnClickListener {
            val dlg = TodoDetailDialog(this)
            dlg.setOnClickListner(
                object : TodoDetailDialog.ButtonClickListner{
                    override fun onRegistClick(toDoItem: ToDoItem) {
                        todoList.add(toDoItem)
                        recycler.adapter?.notifyDataSetChanged()
                    }

                }
            )
            dlg.show(null)
        }




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
                Toast.makeText(this, "all",Toast.LENGTH_SHORT ).show()
            }
            /** 할일 보기 */
            R.id.view_todo -> {
                Toast.makeText(this, "todo",Toast.LENGTH_SHORT ).show()
            }
            /** 완료 목록 보기 */
            R.id.view_done -> {
                Toast.makeText(this, "done",Toast.LENGTH_SHORT ).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}