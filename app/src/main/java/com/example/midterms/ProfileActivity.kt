package com.example.midterms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val usernameTextView: TextView = findViewById(R.id.profile_username)
        val userIdTextView: TextView = findViewById(R.id.profile_user_id)
        val logoutButton: Button = findViewById(R.id.logout_button)
        val resetPinButton: Button = findViewById(R.id.reset_pin_button)

        // Get the data passed from SecondActivity
        val username = intent.getStringExtra("username")
        val rfid = intent.getStringExtra("rfid")

        // Set the text
        usernameTextView.text = username
        userIdTextView.text = "rfid# $rfid"


        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        resetPinButton.setOnClickListener {
            val intent = Intent(this, ResetPinActivity::class.java)
            startActivity(intent)
        }
    }
}
