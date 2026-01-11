package com.example.midterms

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {

    private lateinit var helloTextView: TextView
    private lateinit var roleTextView: TextView
    private lateinit var adminDashboard: LinearLayout
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        helloTextView = findViewById(R.id.helloTextView)
        roleTextView = findViewById(R.id.roleTextView)
        adminDashboard = findViewById(R.id.admin_dashboard)
        logoutButton = findViewById(R.id.logout_button)

        val username = intent.getStringExtra("username")

        helloTextView.text = "Hello, $username!"

        if (username.equals("Admin", ignoreCase = true)) {
            roleTextView.text = "Role: Admin"
            adminDashboard.visibility = View.VISIBLE
        } else {
            roleTextView.text = "Role: User"
            adminDashboard.visibility = View.GONE
        }

        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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
