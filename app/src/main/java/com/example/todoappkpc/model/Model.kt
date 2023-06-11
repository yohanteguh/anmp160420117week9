package com.example.todoappkpc.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @ColumnInfo(name = "judul")
    var title:String,
    @ColumnInfo(name = "catatan")
    var notes:String,
    @ColumnInfo(name = "priority")
    var priority:Int,
    @ColumnInfo(name = "is_done")
    var is_done:Int
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0 //Ditaruh di dalam biar bisa diberi default value
}