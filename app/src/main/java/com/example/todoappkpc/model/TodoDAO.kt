package com.example.todoappkpc.model

import androidx.room.*

@Dao
interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Todo) //vararg artinya banyak var, bisa todo1, todo2

    @Query("SELECT * FROM todo WHERE is_done = 0 ORDER BY priority DESC") //hasilnya berupa list atau single
    fun selectAllTodo():List<Todo>

    @Query("SELECT * FROM todo WHERE uuid = :id") //:id untuk akses parameter
    fun selectTodo(id:Int):Todo

    @Query("UPDATE todo SET judul=:title, catatan=:notes, priority=:priority WHERE uuid=:id")
    fun update(title:String, notes:String, priority:Int, id:Int) //Tidak perlu suspend seperti di slide

    @Query("UPDATE todo SET is_done=1 WHERE uuid=:id")
    fun updateDone(id:Int)

    @Delete
    fun deleteTodo(todo: Todo)
}