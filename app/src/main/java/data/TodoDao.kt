package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import data.model.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE uid = :uid")
    fun selectById(uid: Int): Todo

    @Insert
    fun insert(todo: Todo)

    @Delete
    fun delete(todo: Todo)

}
