package com.example.todoappkpc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.model.TodoDatabase
import com.example.todoappkpc.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application): AndroidViewModel(application), CoroutineScope { //(application: Application): AndroidViewModel(application) diperlukan juga perlu akses context
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private var job = Job()

    //Klik kanan nama class di atas, lalu implement members buat memunculkan override ini
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO //Karena perlu akses database room, maka pakai thread IO

    fun refresh(){
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch { //Apapun yang dijalankan disini akan dijalankan dispatcher IO
            val db = buildDB(getApplication()) //buildDB diambil dari util
            todoLD.postValue(db.todoDao().selectAllTodo()) //Tidak bisa value, melainkan pakai postValue
        }
    }

    fun clearTask(todo: Todo){
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "tododb").build()
            db.todoDao().deleteTodo(todo)
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }

    fun updateDone(uuid:Int){
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "tododb").build()
            db.todoDao().updateDone(uuid)
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
}