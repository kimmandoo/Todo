package com.challenge.todo.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.todo.data.entity.Todo
import com.challenge.todo.model.dao.TodoDao

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}