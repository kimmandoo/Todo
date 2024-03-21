package com.challenge.todo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.todo.data.ToDoItem

class MainViewModel : ViewModel() {
    private val _toDoList = MutableLiveData<List<ToDoItem>>()
    val toDoList : LiveData<List<ToDoItem>> get() = _toDoList


}