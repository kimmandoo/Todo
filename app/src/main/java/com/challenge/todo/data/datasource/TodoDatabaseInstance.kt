package com.challenge.todo.data.datasource

import android.content.Context
import androidx.room.Room

object TodoDatabaseInstance {
    private var instance: TodoDatabase? = null

    fun getDatabase(context: Context): TodoDatabase {
        if (instance == null) {
            synchronized(TodoDatabase::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java, "todo"
                    ).build()
                }
            }
        }
        return instance!!
    }
}