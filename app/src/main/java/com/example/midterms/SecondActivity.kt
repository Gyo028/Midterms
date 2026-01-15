package com.example.midterms

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class SecondActivity : AppCompatActivity() {

    private lateinit var balanceText: TextView
    private var currentBalance = 0.0

    private val loadAmountLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newAmount = result.data?.getIntExtra("LOAD_AMOUNT", 0) ?: 0
            currentBalance += newAmount
            updateBalanceText()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val welcomeText: TextView = findViewById(R.id.welcomeText)
        balanceText = findViewById(R.id.balanceText)
        val loadBtn: Button = findViewById(R.id.loadBtn)

        val username = intent.getStringExtra("username")
        welcomeText.text = "Welcome, $username!"

        updateBalanceText()

        loadBtn.setOnClickListener {
            val intent = Intent(this, LoadActivity::class.java)
            loadAmountLauncher.launch(intent)
        }
    }

    private fun updateBalanceText() {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
        balanceText.text = format.format(currentBalance)
    }
}
