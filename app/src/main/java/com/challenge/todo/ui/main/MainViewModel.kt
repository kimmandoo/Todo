package com.challenge.todo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.todo.data.ToDoItem
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    val _toDoList = MutableLiveData<MutableList<ToDoItem>>()
    private val toDoList = arrayListOf<ToDoItem>()

    fun toggleToDoItem(toDoItem: ToDoItem){
        toDoItem.isDone = !toDoItem.isDone
        _toDoList.value = toDoList
    }

    fun checkButton(toDoItem: ToDoItem){
        toDoItem.dueDate = LocalDateTime.now().toString()
        _toDoList.value = toDoList
    }

    fun deleteItem(position: Int) {
        toDoList.removeAt(position)
        _toDoList.value = toDoList
    }
}