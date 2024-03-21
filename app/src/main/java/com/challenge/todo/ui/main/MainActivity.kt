package com.challenge.todo.ui.main

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.challenge.todo.R
import com.challenge.todo.data.datasource.TodoDatabaseInstance
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.base.BaseActivity
import com.challenge.todo.ui.detail.DetailActivity
import kotlin.math.log

private const val TAG = "MainActivity"
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val todoAdapter by lazy { TodoAdapter() }
    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        val todoDBInstance = TodoDatabaseInstance.getDatabase(context = applicationContext)
        binding.apply {
            adapter = todoAdapter
            viewModel.getListFromRoomDB(todoDao = todoDBInstance.todoDao())
            lifecycleOwner?.let { lifecycleOwner ->
                viewModel.todoList.observe(lifecycleOwner) { todoList ->
                    adapter?.submitList(todoList)
                }
            }


            mainFab.setOnClickListener {
                viewModel.insertTodoItem(
                    todoDao = todoDBInstance.todoDao(),
                    Todo("title", "content")
                )
            }
        }
    }
    private val request =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {

            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.option_done->{
                Log.d(TAG, "onOptionsItemSelected: ")
                request.launch(Intent(baseContext, DetailActivity::class.java))
            }
            R.id.option_todo->{

            }
            R.id.option_all ->{

            }
        }

        return super.onOptionsItemSelected(item)
    }

}