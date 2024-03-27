package com.challenge.todo.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.challenge.todo.data.ToDoItem
import com.challenge.todo.data.db.entity.ToDoEntity
import kotlinx.coroutines.flow.Flow

// 6번까지 완료
@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getToDoList() : Flow<List<ToDoEntity>>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodoItemDetail(id : Int) : Flow<ToDoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDoItem: ToDoEntity)

    @Update
    fun update(toDoItem: ToDoEntity)
    @Delete
    fun delete(toDoItem: ToDoEntity)
}