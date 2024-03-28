package com.challenge.todo.util

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun timeStamp(): String = SimpleDateFormat(dateFormat).format(Date(System.currentTimeMillis()))

fun Activity.easyToast(content: String){
    Toast.makeText(baseContext, content, Toast.LENGTH_SHORT).show()
}