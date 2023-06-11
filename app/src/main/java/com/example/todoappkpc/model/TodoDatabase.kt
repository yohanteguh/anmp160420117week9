package com.example.todoappkpc.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoappkpc.util.MIGRATION_1_2
import com.example.todoappkpc.util.MIGRATION_2_3

@Database(entities = [Todo::class], version = 3) //Ganti version di sini
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao():TodoDAO

    companion object{
        @Volatile private var instance:TodoDatabase ?= null //default null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, "tododb")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3) //Tambah ini apabila melakukan migration
                .build()

        operator fun invoke(context: Context){
            if (instance != null){
                synchronized(LOCK){ //Apabila sudah synchronized, maka yang lain tidak bisa masuk thread tersebut
                    instance?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}