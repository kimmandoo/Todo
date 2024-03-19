package com.challenge.todo.data.dto

data class Todo(
    val title: String,
    val content: String? =null,
    val state: TodoState
)

enum class TodoState{
    TODO,
    DONE,
    ALL
}