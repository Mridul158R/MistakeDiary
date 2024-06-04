package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        auth = Firebase.auth

        val loginText : TextView = findViewById(R.id.tvLoginNow)
        loginText.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.btnRegister)
        registerButton.setOnClickListener{
            performSignup()
        }



    }
    private fun performSignup(){
        val db = Firebase.firestore
        val RegisterEmail = findViewById<EditText>(R.id.etEmailRegister)
        val RegisterPassword = findViewById<EditText>(R.id.etPasswordRegister)
        val RegisterUsername = findViewById<EditText>(R.id.etUsername)
        if(RegisterEmail.text.isEmpty() || RegisterPassword.text.isEmpty() || RegisterUsername.text.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val email = RegisterEmail.text.toString()
        val password = RegisterPassword.text.toString()
        val username = RegisterUsername.text.toString()
//        val user = Users(userName = username, email = email)
        val user = hashMapOf(
            "email" to email,
            "userName" to username,
        )

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success,
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener {
                        }

                    Toast.makeText(
                        baseContext,
                        "Success",
                        Toast.LENGTH_SHORT,
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this,"Error occured ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}