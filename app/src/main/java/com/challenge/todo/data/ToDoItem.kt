package com.challenge.todo.data

data class ToDoItem(
    val id: Int,
    val title: String,
    val content: String,
//    val registerDate: String,
//    var dueDate: String?,
    var isDone: Boolean ){

}