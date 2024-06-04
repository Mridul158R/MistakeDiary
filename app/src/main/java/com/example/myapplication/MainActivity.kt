package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {

    private lateinit var MistakeAdapter: MistakeAdapter
    val database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mistakeItemsrv = findViewById<RecyclerView>(R.id.rvMistakeItems)
        MistakeAdapter = MistakeAdapter(mutableListOf())

        mistakeItemsrv.adapter = MistakeAdapter
        mistakeItemsrv.layoutManager = LinearLayoutManager(this)
        // Write a message to the database

        val userImagebtn = findViewById<ImageButton>(R.id.imageButton)
        userImagebtn.setOnClickListener{
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        val addMistakebtn = findViewById<Button>(R.id.btnAddMistake)
        val mistakeTitleet = findViewById<EditText>(R.id.etMistakeTitle)
        val myRef = database.getReference("mistakes")

        addMistakebtn.setOnClickListener{
//            val myRef = database.getReference("message")


            val mistakeTitle = mistakeTitleet.text.toString()
            if(mistakeTitle.isNotEmpty()){
                val mistake = Mistake(mistakeTitle)
//                MistakeAdapter.addMistake(mistake)
                mistakeTitleet.text.clear()

                myRef.push().setValue(mistakeTitle)
//                myRef.push(mistake)
            }
        }
        myRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val mistakeTitle = snapshot.getValue(String::class.java) ?: return
                val mistake = Mistake(mistakeTitle)
                MistakeAdapter.addMistake(mistake)
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Handle changes if necessary
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Handle removal if necessary
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Handle moves if necessary
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors if necessary
            }

        })



    }
}