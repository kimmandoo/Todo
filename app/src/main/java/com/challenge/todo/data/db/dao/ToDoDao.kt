package com.challenge.todo.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.challenge.todo.data.ToDoItem
// 6번까지 완료
@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getToDoList() : List<ToDoItem>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodoItemDetail(id : Int) : ToDoItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDoItem: ToDoItem)

    @Update
    fun update(toDoItem: ToDoItem)
    @Delete
    fun delete(toDoItem: ToDoItem)
}