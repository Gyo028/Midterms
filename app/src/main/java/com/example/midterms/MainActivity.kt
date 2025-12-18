package com.example.midterms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var titleTextView: TextView
    lateinit var usernameEditText: EditText

    lateinit var passwordEditText: EditText
    lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.title)
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        createButton = findViewById(R.id.createButton)

        createButton.setOnClickListener {
            val username = usernameEditText.text.toString()

            if (username.isEmpty()) {
                Toast.makeText(this, "Username can't be empty", Toast.LENGTH_SHORT).show()
            } else if (username.length < 3) {
                Toast.makeText(this, "Username must be at least 3 characters", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            }

            usernameEditText.text.clear()
            passwordEditText.text.clear()



        }

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View>(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
