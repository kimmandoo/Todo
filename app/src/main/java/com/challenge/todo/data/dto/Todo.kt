package com.challenge.todo.data.dto

data class Todo(
    val title: String,
    val content: String? =null,
    val date: String,
    val state: Int
)

enum class TodoState(state: Int){
    TODO(0),
    DONE(1),
    ALL(2)
}