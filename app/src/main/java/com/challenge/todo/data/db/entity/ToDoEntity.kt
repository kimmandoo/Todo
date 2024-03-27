package com.challenge.todo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo")
data class ToDoEntity (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val title: String,
    @ColumnInfo val content: String,
//    @ColumnInfo val registerDate: String,
//    @ColumnInfo var dueDate: String?,
    @ColumnInfo var isDone: Boolean

)

