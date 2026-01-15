package com.example.midterms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener(navListener)

        // Load the HomeFragment by default
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }
    }

    private val navListener = NavigationBarView.OnItemSelectedListener { item ->
        lateinit var selectedFragment: Fragment

        when (item.itemId) {
            R.id.navigation_home -> {
                selectedFragment = HomeFragment()
            }
            R.id.navigation_inbox -> {
                selectedFragment = InboxFragment()
            }
            R.id.navigation_transactions -> {
                selectedFragment = TransactionsFragment()
            }
            R.id.navigation_spending -> {
                selectedFragment = SpendingFragment()
            }
            else -> return@OnItemSelectedListener false
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
        true
    }
}
