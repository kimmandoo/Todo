package com.challenge.todo.data.dto

data class Todo(
    val id: Int?,
    val title: String,
    val content: String? = null,
    val date: String? = null,
    val state: Int? =null
)

enum class TodoState(val state: Int) {
    TODO(0),
    DONE(1),
    ALL(2)
}