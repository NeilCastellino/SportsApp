package com.neil.castellino.sports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neil.castellino.sports.databinding.ActivityMainBinding
import com.neil.castellino.sports.fragments.HighlightsFragment
import com.neil.castellino.sports.fragments.NewsFragment
import com.neil.castellino.sports.fragments.PremiumFragment
import com.neil.castellino.sports.fragments.ScoresFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
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