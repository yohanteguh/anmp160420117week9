package com.example.todoappkpc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<Todo>()

    private val job = Job()

    fun fetch(uuid:Int){
        launch { //Apapun yang dijalankan disini akan dijalankan dispatcher IO
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid)) //Di slide salah, seharusnya postValue karena tidak jelas initializenya kapan
        }
    }

    fun update(title:String, notes:String, priority:Int, uuid:Int){
        launch {
            val db = buildDB(getApplication())
            db.todoDao().update(title,notes,priority,uuid)
        }
    }

    fun addTodo(todo: Todo){
        launch { //Apapun yang dijalankan disini akan dijalankan dispatcher IO
            val db = buildDB(getApplication())
            db.todoDao().insertAll(todo) //Satu single to do saja dibandingkan dengan slide yang berupa array" to do
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

}