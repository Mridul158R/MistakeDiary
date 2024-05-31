package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    val database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val todoItemsrv = findViewById<RecyclerView>(R.id.rvTodoItems)
        todoAdapter = TodoAdapter(mutableListOf())

        todoItemsrv.adapter = todoAdapter
        todoItemsrv.layoutManager = LinearLayoutManager(this)
        // Write a message to the database



        val addTodobtn = findViewById<Button>(R.id.btnAddTodo)
        val deleteTodobtn = findViewById<Button>(R.id.btnDeleteDoneTodo)
        val todoTitleet = findViewById<EditText>(R.id.etTodoTitle)
        addTodobtn.setOnClickListener{
            val myRef = database.getReference("message")

            myRef.setValue("Hello, World! 1")
            val todoTitle = todoTitleet.text.toString()
            if(todoTitle.isNotEmpty()){
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                todoTitleet.text.clear()
            }
        }

        deleteTodobtn.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }

    }
}