package com.challenge.todo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "state") val state: Int? = null
): Serializable