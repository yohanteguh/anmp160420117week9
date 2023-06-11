package com.example.todoappkpc.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappkpc.R
import com.example.todoappkpc.model.Todo

class TodoListAdapter(val todos:ArrayList<Todo>, val adapter: (Todo) -> Unit) //Buat akses fragment?,
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() { //Adapter<> perlu ViewHolder class
    class TodoViewHolder(val view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val checkTask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checkTask.text = todos[position].title
        checkTask.isChecked = false //Ini buat masalah kalo cek yang pertama hilang juga di bawahnya

        checkTask.setOnCheckedChangeListener { compoundButton, isChecked -> //Adapter tidak bisa akses view, hanya fragment yang bisa sehingga masukkan adapter di constructor atas
            // b di bagian atas merupakan boolean dan dapat diganti isChecked
            if (isChecked){
//                adapter(todos[position])
            }
        }

        val imgEdit = holder.view.findViewById<ImageView>(R.id.imgEdit)
        imgEdit.setOnClickListener {
            // Bernavigasi ke halaman Edit
            val uuid = todos[position].uuid
            val action = TodoListFragmentDirections.actionEditTodoFragment(uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateTodoList(newTodos:ArrayList<Todo>){
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged() //Mengambil kembali onBindViewHolder
    }

}