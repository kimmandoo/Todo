package com.challenge.todo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.todo.R
import com.challenge.todo.data.ToDoAdapter
import com.challenge.todo.data.ToDoApplication
import com.challenge.todo.data.ToDoItem
import com.challenge.todo.data.db.entity.ToDoEntity
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.todo_detail.TodoDetailDialog

private const val TAG = "MainActivity";
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as ToDoApplication).repo)
    }
    private val todoList = arrayListOf<ToDoItem>(ToDoItem(id = 0, title = "title", content = "content",  isDone = false))

    private val recycler: RecyclerView by lazy {
        binding.toDoListRecyclerview
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = ToDoAdapter()

        recycler.adapter = adapter.apply {
            itemClickListner = object : ToDoAdapter.ItemClickListener{
                override fun OnClick(view: View, position: Int) {
                    val dlg = TodoDetailDialog(this@MainActivity)
                    dlg.setOnClickListner(
                        object : TodoDetailDialog.ButtonClickListner{
                            override fun onRegistClick(toDoItem: ToDoEntity
                            ) {
                                // ToDoItem 받아옴
                                viewModel.insert(toDoItem)
                            }

                        }
                    )
//                    dlg.show(viewModel.select(position))
                }

                override fun deleteItem(position: Int) {
                    TODO("Not yet implemented")
                }

            }
        }
        recycler.layoutManager = LinearLayoutManager(this)

        viewModel.toDoList.observe(this, Observer{
            todo -> todo?.let { adapter.submitList(it) }
        })

        val toDoItemList : MutableList<ToDoItem> =
            mutableListOf(ToDoItem(
                id = 1,
                title = "title", content = "content",  isDone = false))



        binding.createTodoBtn.setOnClickListener {
            val dlg = TodoDetailDialog(this)
            dlg.setOnClickListner(
                object : TodoDetailDialog.ButtonClickListner{
                    override fun onRegistClick(toDoItem: ToDoEntity
                    ) {
                        // ToDoItem 받아옴
                        viewModel.insert(toDoItem)
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