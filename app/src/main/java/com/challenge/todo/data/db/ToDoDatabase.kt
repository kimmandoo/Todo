package com.challenge.todo.data.db

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.challenge.todo.data.ToDoItem
import com.challenge.todo.data.db.dao.ToDoDao
import com.challenge.todo.data.db.entity.ToDoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Room DB에 맞춤 콜백을 만듬 -> 코루틴 스콥을 매개변수로 가져옴
@Database(entities = [ToDoEntity::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todoDao() : ToDoDao

    private class ToDoDatabasesCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Instance?.let { database ->
                scope.launch {
                    populationDatabase(database.todoDao())
                }
            }
        }
        suspend fun populationDatabase(toDoDao: ToDoDao){
            // Delete

            //insert 예제
            toDoDao.insert(
                ToDoEntity(
                id = 1,
                title = "title", content = "content",  isDone = false)
            )
        }
    }

    companion object{
        @Volatile
        private var Instance : ToDoDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): ToDoDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, ToDoDatabase::class.java, "todo_database")
                    .addCallback(ToDoDatabasesCallback(scope))
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}