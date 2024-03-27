package com.challenge.todo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.TodoDao
import data.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _todoList

    fun getAllTodos(todoDao: TodoDao) {
        viewModelScope.launch(Dispatchers.IO) {
            _todoList.postValue(todoDao.getAll())
        }
    }

    fun deleteTodo(todoDao: TodoDao, todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.delete(todo)
            getAllTodos(todoDao)
        }

    }

}