package com.challenge.todo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.challenge.todo.data.ToDoItem
import com.challenge.todo.data.db.entity.ToDoEntity
import com.challenge.todo.data.db.repo.ToDoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

private const val TAG = "MainViewModel"
class MainViewModel(private val toDoRepo: ToDoRepo) : ViewModel() {
   private val _todolist = MutableLiveData<List<ToDoEntity>>()
    val toDoList : LiveData<List<ToDoEntity>>  = _todolist
    fun insert(toDoItem: ToDoEntity) = viewModelScope.launch(Dispatchers.IO) {
        toDoRepo.insertToDo(toDoItem)
    }
    fun select(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        toDoRepo.getTodoItemDetail(id)
    }
}

class MainViewModelFactory(private val repo: ToDoRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


