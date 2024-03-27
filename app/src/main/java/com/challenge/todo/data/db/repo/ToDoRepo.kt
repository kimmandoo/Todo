package com.challenge.todo.data.db.repo


import com.challenge.todo.data.ToDoItem
import com.challenge.todo.data.db.dao.ToDoDao
import com.challenge.todo.data.db.entity.ToDoEntity
import kotlinx.coroutines.flow.Flow
class ToDoRepo(private val toDoDao: ToDoDao) {

    val allToDoList : Flow<List<ToDoEntity>> = toDoDao.getToDoList()

    fun getTodoItemDetail(id : Int) : Flow<ToDoEntity> = toDoDao.getTodoItemDetail(id)

    suspend fun insertToDo(toDoItem: ToDoEntity) = toDoDao.insert(toDoItem)

    fun updateToDo(toDoItem: ToDoEntity) = toDoDao.update(toDoItem)

    fun deleteToDo(toDoItem: ToDoEntity) = toDoDao.delete(toDoItem)
}