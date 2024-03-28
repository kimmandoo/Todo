package com.challenge.todo.ui.main

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import com.challenge.todo.R
import com.challenge.todo.data.datasource.TodoDatabase
import com.challenge.todo.data.datasource.TodoDatabaseInstance
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.data.dto.TodoState
import com.challenge.todo.databinding.ActivityMainBinding
import com.challenge.todo.databinding.BottomsheetCreateBinding
import com.challenge.todo.databinding.BottomsheetDetailBinding
import com.challenge.todo.ui.base.BaseActivity
import com.challenge.todo.util.easyToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


private const val TAG = "MainActivity"

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()
    lateinit var todoDBInstance: TodoDatabase

    private val todoAdapter by lazy {
        TodoAdapter(
            onTodoItemClick = { todo: Todo ->
                showTodoDetail(todo)
            }, onTodoItemDelete = { todo: Todo ->
                viewModel.deleteTodoItem(todoDBInstance.todoDao(), todo)
            }, onTodoItemChecked = { todo: Todo, isChecked: Boolean ->
                if (isChecked) {
                    viewModel.selectTodoList(todo)
                } else {
                    viewModel.deselectTodoList(todo)
                }
            })
    }

    override fun initView() {
        todoDBInstance = TodoDatabaseInstance.getDatabase(context = applicationContext)
        initUI()
        binding.apply {
            viewModel.getTodoList(todoDao = todoDBInstance.todoDao())
            lifecycleOwner?.let { lifecycleOwner ->
                viewModel.todoList.observe(lifecycleOwner) { todoList ->
                    adapter?.submitList(todoList)
                }
                viewModel.selectList.observe(lifecycleOwner) { selectList ->
                    Log.d(TAG, "initView: $selectList")
                    if (selectList.isNotEmpty()) {
                        binding.mainDone.visibility = View.VISIBLE
                    } else {
                        binding.mainDone.visibility = View.INVISIBLE
                    }
                }
            }

            mainFab.setOnClickListener {
                showTodoCreate()
            }
            mainDone.setOnClickListener {
                doneTodo()
            }
        }
    }

    private fun doneTodo() {
        binding.mainDone.visibility = View.GONE
        for (item in viewModel.selectList.value!!) {
            viewModel.finishTodoItem(todoDBInstance.todoDao(), item.value)
        }
        viewModel.deselectAll()
    }

    private fun showTodoCreate() {
        // bottomsheetdialog를 dismiss해도 dim처리된 게 남아있는 경우가 가끔 발생 -> 이유 모르겠음
        val bottomSheetView = BottomsheetCreateBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        bottomSheetDialog.apply {
            setContentView(bottomSheetView.root)
            setOnDismissListener {
                val window = bottomSheetDialog.window
                window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
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
            val state = when (todo.state) {
                TodoState.TODO.state -> "TODO"
                TodoState.ALL.state -> "ALL"
                TodoState.DONE.state -> "DONE"
                else -> {}
            }
            val textStamp = "${todo.date}에 등록된 메모입니다 : $state"
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_done -> {
                Log.d(TAG, "onOptionsItemSelected: done")
                viewModel.getDoneList(todoDBInstance.todoDao())
            }

            R.id.option_todo -> {
                Log.d(TAG, "onOptionsItemSelected: done")
                viewModel.getTodoList(todoDBInstance.todoDao())
            }

            R.id.option_all -> {
                Log.d(TAG, "onOptionsItemSelected: done")
                viewModel.getAllList(todoDBInstance.todoDao())
                // viewModel.clearTodoAll(todoDBInstance.todoDao())
            }
        }
        return super.onOptionsItemSelected(item)
    }

}