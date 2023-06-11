package com.example.todoappkpc.util

import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.todoappkpc.model.TodoDatabase

val DB_NAME = "tododb"

fun buildDB(context:Context):TodoDatabase{
    val db = Room.databaseBuilder(context, TodoDatabase::class.java, DB_NAME)
        .addMigrations(MIGRATION_2_3) //Tambah ini apabila melakukan migration
        .build()
    return db
}

val MIGRATION_1_2 = object : Migration(1, 2) { //object error, implement members!
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo " +
                "ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
        database.execSQL("INSERT INTO todo(judul,catatan,priority) " +
                "VALUES('Example Todo', 'Example Notes', 3)") //Tidak ada di slide
    }

}

val MIGRATION_2_3 = object : Migration(2, 3) { //object error, implement members!
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo " +
                "ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL") //Karena Room tidak memiliki Boolean, melainkan Integer dengan nilai 0 atau 1
    }

}