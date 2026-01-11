package com.example.midterms

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Patterns


class MainActivity : AppCompatActivity() {

    lateinit var titleTextView: TextView
    lateinit var signInTextView: TextView
    lateinit var usernameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var confirmPasswordEditText: EditText
    lateinit var termsCheckBox: CheckBox
    lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.title)
        signInTextView = findViewById(R.id.signInText)
        usernameEditText = findViewById(R.id.username)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)
        termsCheckBox = findViewById(R.id.termsCheckBox)
        createButton = findViewById(R.id.createButton)

        createButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val termsChecked = termsCheckBox.isChecked

            when{
                username.isEmpty() -> {
                    Toast.makeText(this, "Username can't be empty", Toast.LENGTH_SHORT).show()
                }
                username.length < 3 -> {
                    Toast.makeText(this, "Username must be at least 3 characters", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Use a valid email address", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show()
                }
                password.length < 3 -> {
                    Toast.makeText(this, "Password must be at least 3 characters", Toast.LENGTH_SHORT).show()
                }
                confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show()
                }
                confirmPassword != password -> {
                    Toast.makeText(this, "Password must match", Toast.LENGTH_SHORT).show()
                }
                !termsChecked -> {
                    Toast.makeText(this, "You must check the terms and conditions", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)

                    usernameEditText.text.clear()
                    emailEditText.text.clear()
                    passwordEditText.text.clear()
                    confirmPasswordEditText.text.clear()
                    termsCheckBox.isChecked = false
                }

            }

        }

        val text = getString(R.string.sign_in_text)
        val ss = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#DDA0FF")
                ds.isUnderlineText = true
            }
        }

        val startIndex = text.indexOf("Sign in")
        val endIndex = startIndex + "Sign in".length()

        if (startIndex != -1) {
            ss.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            signInTextView.text = ss
            signInTextView.movementMethod = LinkMovementMethod.getInstance()
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
