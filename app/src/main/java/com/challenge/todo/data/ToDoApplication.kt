package com.challenge.todo.data

import android.app.Application
import com.challenge.todo.data.db.ToDoDatabase
import com.challenge.todo.data.db.repo.ToDoRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// application 클래스는 mainifest 파일 업데이트
class ToDoApplication : Application() {

    // 코루틴을 사용하기 위한 스코프
    val applicationScope = CoroutineScope(SupervisorJob())

    // 데이터 베이스에 기반하여 인스턴스 만들기
    // 처음 필요할 때만 만들기 때문에 lazy 속성 사용
    val database by lazy {
        ToDoDatabase.getDatabase(this,applicationScope)
    }

    val repo by lazy {
        ToDoRepo(database.todoDao())
    }
}