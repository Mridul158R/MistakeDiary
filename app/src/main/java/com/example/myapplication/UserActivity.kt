package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        val logOutbtn = findViewById<Button>(R.id.btnLogOut)
        logOutbtn.setOnClickListener{
            Firebase.auth.signOut()
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val emailtv = findViewById<TextView>(R.id.tvEmail)
        val usernametv = findViewById<TextView>(R.id.tvUsername)
        user?.let {
            emailtv.text = it.email
//            db.collection("users") // Assuming your collection is named "users"
//                .whereEqualTo("email", it.email)
//                .get()
//                .addOnSuccessListener { documents ->
//                    if (documents.isEmpty) {
//                        Log.d("Firestore", "No matching documents found.")
//
//                    } else {
//                        for (document in documents) {
//                            val userdb = document.toObject(Users::class.java)
//                            usernametv.text = userdb.userName
//                        }
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("Firestore", "Error getting documents: ", exception)
//
//                }
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val fireuser = document.data
                        val fireEmail = fireuser["email"] as? String
                        if(fireEmail == it.email){
                            val username = fireuser["userName"] as? String
                            usernametv.text = username
                            break // Exit loop once the user is found
                        }
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }




    }
}