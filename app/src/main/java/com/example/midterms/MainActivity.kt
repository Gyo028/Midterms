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

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var pinEditText: EditText
    private lateinit var confirmPinEditText: EditText
    private lateinit var termsCheckBox: CheckBox
    private lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.username)
        emailEditText = findViewById(R.id.email)
        pinEditText = findViewById(R.id.pin)
        confirmPinEditText = findViewById(R.id.confirmPin)
        termsCheckBox = findViewById(R.id.termsCheckBox)
        createButton = findViewById(R.id.createButton)

        createButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val pin = pinEditText.text.toString()
            val confirmPin = confirmPinEditText.text.toString()
            val termsChecked = termsCheckBox.isChecked

            // Clear previous errors
            usernameEditText.error = null
            emailEditText.error = null
            pinEditText.error = null
            confirmPinEditText.error = null

            when {
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
                pin.isEmpty() -> {
                    pinEditText.error = "PIN can't be empty"
                }
                pin.length != 6 -> {
                    pinEditText.error = "PIN must be exactly 6 digits"
                }
                confirmPin != pin -> {
                    confirmPinEditText.error = "PINs must match"
                }
                !termsChecked -> {
                    Toast.makeText(this, "You must agree to the Terms and Conditions", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

                    // Navigate directly to the Home Screen (SecondActivity)
                    val intent = Intent(this, SecondActivity::class.java)
                    // Pass the new user's data to the home screen
                    intent.putExtra("username", username)
                    intent.putExtra("rfid", "26-0003") // Assigning a mock RFID for the new user

                    // Clear the back stack so the user can't go back to the registration screen
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }

        val signInTextView: TextView = findViewById(R.id.signInText)
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
