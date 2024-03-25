package com.challenge.todo.ui.main

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.challenge.todo.R
import com.challenge.todo.data.datasource.TodoDatabase
import com.challenge.todo.data.datasource.TodoDatabaseInstance
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.databinding.BottomsheetCreateBinding
import com.challenge.todo.databinding.BottomsheetDetailBinding
import com.challenge.todo.ui.base.BaseActivity
import com.challenge.todo.util.easyToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

private const val TAG = "MainActivity"

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val todoAdapter by lazy {
        TodoAdapter(onTodoItemClick = { todo: Todo ->
            showTodoDetail(todo)
        }, onTodoItemDelete = { todo: Todo ->
            viewModel.deleteTodoItem(todoDBInstance.todoDao(), todo)
        })
    }
    override val viewModel: MainViewModel by viewModels()
    lateinit var todoDBInstance: TodoDatabase

    override fun initView() {
        todoDBInstance = TodoDatabaseInstance.getDatabase(context = applicationContext)
        initUI()
        binding.apply {
            viewModel.getListFromRoomDB(todoDao = todoDBInstance.todoDao())
            lifecycleOwner?.let { lifecycleOwner ->
                viewModel.todoList.observe(lifecycleOwner) { todoList ->
                    adapter?.submitList(todoList)
                }
            }
            mainFab.setOnClickListener {
                showTodoCreate()
            }
        }
    }

    private fun showTodoCreate() {
        val bottomSheetView = BottomsheetCreateBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        bottomSheetDialog.apply {
            setContentView(bottomSheetView.root)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }.show()
        bottomSheetView.apply {
            bsBtnCreate.setOnClickListener {
                viewModel.insertTodoItem(
                    todoDao = todoDBInstance.todoDao(),
                    Todo(null, bsTitle.text.toString(), bsDetail.text.toString())
                )
                bottomSheetDialog.dismiss()
            }
        }
    }

    private fun showTodoDetail(todo: Todo) {
        val bottomSheetView = BottomsheetDetailBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        bottomSheetDialog.apply {
            setContentView(bottomSheetView.root)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }.show()
        bottomSheetView.apply {
            val textStamp = "${todo.date}에 등록된 메모입니다 : ${todo.state}"
            bsTvState.text = textStamp
            bsDetail.setText(todo.content)
            bsTitle.setText(todo.title)
            easyToast(todo.toString())
            bsBtnModify.setOnClickListener {
                viewModel.updateTodoItem(
                    todoDao = todoDBInstance.todoDao(),
                    Todo(
                        todo.id,
                        todo.title,
                        bsDetail.text.toString(),
                        todo.date,
                        todo.state
                    )
                )
                bottomSheetDialog.dismiss()
            }
        }
    }

    private fun initUI() {
        binding.apply {
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
                viewModel.clearTodoAll(todoDBInstance.todoDao())
            }
        }
        return super.onOptionsItemSelected(item)
    }

}