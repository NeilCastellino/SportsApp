package com.neil.castellino.sports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_news -> showFragment(NewsFragment())
                R.id.navigation_scores -> showFragment(ScoresFragment())
                R.id.navigation_highlights -> showFragment(HighlightsFragment())
                R.id.navigation_premium -> showFragment(PremiumFragment())
            }
            true
        }

        if (savedInstanceState == null) {
            showFragment(NewsFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}