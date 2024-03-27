package com.challenge.todo.ui.main

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
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
import java.util.LinkedList


private const val TAG = "MainActivity"

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    val todoStack = mutableMapOf<Int, Todo>()
    private val todoAdapter by lazy {
        TodoAdapter(onTodoItemClick = { todo: Todo ->
            showTodoDetail(todo)
        }, onTodoItemDelete = { todo: Todo ->
            viewModel.deleteTodoItem(todoDBInstance.todoDao(), todo)
        }, onTodoItemChecked = { todo: Todo, isChecked: Boolean ->
            if (isChecked){
                todoStack[todo.id!!] = todo
            }else{
                todoStack.remove(todo.id!!)
            }
            if(todoStack.isNotEmpty()){
                binding.mainDone.visibility = View.VISIBLE
            }else{
                binding.mainDone.visibility = View.INVISIBLE
            }
            Log.d(TAG, "stack: ${todoStack.toString()}")
            // update할 리스트에 추가하면서,  check일 때 추가하고, false일 때 뺀다.
            // 만약 상단의 btn을 눌러 확정하면, 현재 보는 화면에서 없어지고, state가 todo에서 done으로 바뀌고, update 됨

        })
    }
    override val viewModel: MainViewModel by viewModels()
    lateinit var todoDBInstance: TodoDatabase

    override fun initView() {
        todoDBInstance = TodoDatabaseInstance.getDatabase(context = applicationContext)
        initUI()
        binding.apply {
            viewModel.getAllList(todoDao = todoDBInstance.todoDao())
            lifecycleOwner?.let { lifecycleOwner ->
                viewModel.todoList.observe(lifecycleOwner) { todoList ->
                    adapter?.submitList(todoList)
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

    private fun doneTodo(){
        binding.mainDone.visibility = View.GONE
        for(item in todoStack){
            viewModel.updateTodoItem(todoDBInstance.todoDao(), item.value)
        }
    }

    private fun showTodoCreate() {
        // bottomsheetdialog를 dismiss해도 dim처리된 게 남아있는 경우가 가끔 발생 -> 이유 모르겠음
        val bottomSheetView = BottomsheetCreateBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        bottomSheetDialog.apply {
            setContentView(bottomSheetView.root)
//            setOnDismissListener {
//                val window = bottomSheetDialog.window
//                window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//            }
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