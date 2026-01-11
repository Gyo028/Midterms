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
            // Clear previous errors
            usernameEditText.error = null
            emailEditText.error = null
            passwordEditText.error = null
            confirmPasswordEditText.error = null
            
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val termsChecked = termsCheckBox.isChecked

            when{
                username.isEmpty() -> {
                    usernameEditText.error = "Username can't be empty"
                }
                username.length < 3 -> {
                    usernameEditText.error = "Username must be at least 3 characters"
                }
                email.isEmpty() -> {
                    emailEditText.error = "Email can't be empty"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailEditText.error = "Use a valid email address"
                }
                password.isEmpty() -> {
                    passwordEditText.error = "Password can't be empty"
                }
                password.length < 8 -> {
                    passwordEditText.error = "Password must be at least 8 characters"
                }
                !password.any { it.isDigit() } -> {
                    passwordEditText.error = "Password must contain a number"
                }
                !password.any { !it.isLetterOrDigit() } -> {
                    passwordEditText.error = "Password must contain a special character"
                }
                confirmPassword != password -> {
                    confirmPasswordEditText.error = "Passwords must match"
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
        val endIndex = startIndex + "Sign in".length

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
