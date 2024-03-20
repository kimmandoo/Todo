package com.challenge.todo.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

const val format = "MM-dd HH:mm"

@SuppressLint("SimpleDateFormat")
val formattedDate = SimpleDateFormat(format)

