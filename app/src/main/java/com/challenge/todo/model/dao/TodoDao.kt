package com.challenge.todo.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.challenge.todo.data.entity.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM todo WHERE id=:id")
    fun getTodo(id: Int): TodoEntity

    @Insert
    fun insert(todoEntity: TodoEntity)

    @Update
    fun update(todoEntity: TodoEntity)

    @Delete
    fun delete(todoEntity: TodoEntity)

}