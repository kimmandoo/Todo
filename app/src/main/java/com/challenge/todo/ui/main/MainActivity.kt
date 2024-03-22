package com.challenge.todo.ui.main

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.challenge.todo.R
import com.challenge.todo.data.datasource.TodoDatabaseInstance
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.ui.base.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

private const val TAG = "MainActivity"

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val todoAdapter by lazy {
        TodoAdapter(onTodoItemClick = { todo: Todo ->
            showTodoDetail(todo)
        })
    }
    private lateinit var bottomSheetDialog: BottomSheetDialog
    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        val todoDBInstance = TodoDatabaseInstance.getDatabase(context = applicationContext)
        initUI()
        binding.apply {
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

    private fun showTodoDetail(todo: Todo) {
        bottomSheetDialog.show()
        bottomSheetDialog.findViewById<TextView>(R.id.bottomsheet)?.text = todo.toString()
        Toast.makeText(this, todo.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initUI(){
        binding.apply {
            val bottomSheetView = layoutInflater.inflate(R.layout.bottomsheet_detail, null)
            bottomSheetDialog = BottomSheetDialog(this@MainActivity)
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            adapter = todoAdapter
            setSupportActionBar(mainToolbar)
        }
    }

    private val request =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {

            }
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_done -> {
                Log.d(TAG, "onOptionsItemSelected: done")

            }

            R.id.option_todo -> {
                Log.d(TAG, "onOptionsItemSelected: done")

            }

            R.id.option_all -> {
                Log.d(TAG, "onOptionsItemSelected: done")

            }
        }
        return super.onOptionsItemSelected(item)
    }

}