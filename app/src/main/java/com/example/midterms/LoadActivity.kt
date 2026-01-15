package com.example.midterms

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class LoadActivity : AppCompatActivity() {

    private lateinit var amountInput: EditText
    private lateinit var selectedAmountChecker: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_activity)

        amountInput = findViewById(R.id.amountInput)
        selectedAmountChecker = findViewById(R.id.selectedAmountChecker)
        val btn50: Button = findViewById(R.id.btn50)
        val btn100: Button = findViewById(R.id.btn100)
        val btn150: Button = findViewById(R.id.btn150)
        val btn200: Button = findViewById(R.id.btn200)
        val confirmButton: Button = findViewById(R.id.confirmButton)

        // Listen for text changes to update the checker
        amountInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateChecker()
            }
        })

        // Set click listeners for the amount buttons
        btn50.setOnClickListener { addAmount(50) }
        btn100.setOnClickListener { addAmount(100) }
        btn150.setOnClickListener { addAmount(150) }
        btn200.setOnClickListener { addAmount(200) }

        confirmButton.setOnClickListener {
            val amount = amountInput.text.toString().toIntOrNull() ?: 0
            val resultIntent = Intent()
            resultIntent.putExtra("LOAD_AMOUNT", amount)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun addAmount(amount: Int) {
        val currentAmount = amountInput.text.toString().toIntOrNull() ?: 0
        val newAmount = currentAmount + amount
        amountInput.setText(newAmount.toString())
    }

    private fun updateChecker() {
        val amount = amountInput.text.toString().toIntOrNull() ?: 0
        val format = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
        selectedAmountChecker.text = "You are adding: ${format.format(amount)}"
    }
}
