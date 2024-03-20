package com.challenge.todo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.todo.data.ToDoItem

class MainViewModel : ViewModel() {
    private val toDoList: MutableLiveData<List<ToDoItem>> by lazy {
        MutableLiveData().also {

        }
    }
}