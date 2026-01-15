package com.example.midterms

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.Locale

class HomeFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val welcomeText: TextView = view.findViewById(R.id.welcomeText)
        val userIdText: TextView = view.findViewById(R.id.userIdText)
        balanceText = view.findViewById(R.id.balanceText)
        val loadBtn: Button = view.findViewById(R.id.loadBtn)
        val profileIcon: ImageView = view.findViewById(R.id.profileIcon)

        // The username and rfid are passed to the activity, which we can access here
        val username = activity?.intent?.getStringExtra("username")
        val rfid = activity?.intent?.getStringExtra("rfid")

        welcomeText.text = "Welcome, $username!"
        userIdText.text = "rfid# $rfid"

        updateBalanceText()

        loadBtn.setOnClickListener {
            val intent = Intent(activity, LoadActivity::class.java)
            loadAmountLauncher.launch(intent)
        }

        profileIcon.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("rfid", rfid)
            startActivity(intent)
        }

        return view
    }

    private fun updateBalanceText() {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
        balanceText.text = format.format(currentBalance)
    }
}
