package com.challenge.todo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.todo.data.dto.Todo
import com.challenge.todo.data.dto.TodoState
import com.challenge.todo.data.entity.TodoEntity
import com.challenge.todo.domain.model.dao.TodoDao
import com.challenge.todo.util.timeStamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _todoList

    //    val _selectList = mutableMapOf<Int, Todo>()
    private val _selectList = MutableLiveData<MutableMap<Int, Todo>>(mutableMapOf()) //
    val selectList: LiveData<MutableMap<Int, Todo>> = _selectList

    fun selectTodoList(todo: Todo) {
        _selectList.value?.put(todo.id!!, todo)
        Log.d(TAG, "todo: $todo , selectTodoList: ${_selectList.value.toString()}")
    }

    fun deselectTodoList(todo: Todo) {
        _selectList.value?.remove(todo.id!!)
        Log.d(TAG, "todo: $todo , deselectTodoList: ${_selectList.value.toString()}")
    }

    fun deselectAll() {
        _selectList.value?.clear()
    }

    fun getAllList(todoDao: TodoDao) {
        viewModelScope.launch {
            lateinit var list: List<Todo>
            withContext(Dispatchers.IO) {
                list = todoDao.getAll()
                    .map { Todo(it.id, it.title, it.content, it.date!!) }
            }
            _todoList.value = list
        }
    }

    fun getDoneList(todoDao: TodoDao) {
        viewModelScope.launch {
            lateinit var list: List<Todo>
            withContext(Dispatchers.IO) {
                list = todoDao.getAll().filter { it.state == 1 }
                    .map { Todo(it.id, it.title, it.content, it.date!!, TodoState.DONE) }
            }
            _todoList.value = list
        }
    }

    fun getTodoList(todoDao: TodoDao) {
        viewModelScope.launch {
            lateinit var list: List<Todo>
            withContext(Dispatchers.IO) {
                list = todoDao.getAll().filter { it.state == 0 }
                    .map { Todo(it.id, it.title, it.content, it.date!!, TodoState.TODO) }
            }
            _todoList.value = list
        }
    }


    fun clearTodoAll(todoDao: TodoDao) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.clear()
            getAllList(todoDao)
        }
    }

    fun deleteTodoItem(todoDao: TodoDao, todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao
                .delete(
                    TodoEntity(
                        todo.id,
                        todo.title,
                        todo.content,
                        todo.date,
                    )
                )
            getAllList(todoDao)
        }
    }

    fun finishTodoItem(todoDao: TodoDao, todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao
                .update(
                    TodoEntity(
                        todo.id,
                        todo.title,
                        todo.content,
                        todo.date,
                        TodoState.DONE.ordinal
                    )
                )
        }
        getAllList(todoDao)
    }

    fun updateTodoItem(todoDao: TodoDao, todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao
                .update(
                    TodoEntity(
                        todo.id,
                        todo.title,
                        todo.content,
                        todo.date,
                        TodoState.TODO.ordinal
                    )
                )
        }
        getAllList(todoDao)
    }

    fun insertTodoItem(todoDao: TodoDao, todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao
                .insert(
                    TodoEntity(
                        null,
                        todo.title,
                        todo.content,
                        timeStamp(),
                        TodoState.TODO.ordinal
                    )
                )
            getAllList(todoDao)
        }
    }


}