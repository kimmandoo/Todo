package com.challenge.todo.ui.main

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

class MainViewModel : ViewModel() {
    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _todoList

    fun getListFromRoomDB(todoDao: TodoDao) {
        viewModelScope.launch {
            lateinit var list: List<Todo>
            withContext(Dispatchers.IO) {
                list = todoDao.getAll().map { Todo(it.id,it.title, it.content, it.date!!, TodoState.ALL) }
            }
            _todoList.value = list
        }
    }

    fun updateTodoItem(todoDao: TodoDao, todo: Todo){
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
        getListFromRoomDB(todoDao)
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
        }
        getListFromRoomDB(todoDao)
    }
}