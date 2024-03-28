package com.challenge.todo.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.todo.data.entity.TodoEntity
import com.challenge.todo.domain.model.dao.TodoDao

@Database(entities = [TodoEntity::class], version = 2, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}