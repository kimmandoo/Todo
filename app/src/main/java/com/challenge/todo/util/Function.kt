package com.challenge.todo.util

import android.app.Activity
import android.widget.Toast
import java.util.Date

fun timeStamp(): String = formattedDate.format(Date(System.currentTimeMillis()))
fun Activity.easyToast(content: String){
    Toast.makeText(baseContext, content, Toast.LENGTH_SHORT).show()
}