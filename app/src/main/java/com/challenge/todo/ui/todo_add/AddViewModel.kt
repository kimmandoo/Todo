package com.challenge.todo.ui.todo_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.TodoDao
import data.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {

    fun addTodo(todoDao: TodoDao, todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insert(todo)
        }
    }


}