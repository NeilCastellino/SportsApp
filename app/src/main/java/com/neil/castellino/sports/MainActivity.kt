package com.neil.castellino.sports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neil.castellino.sports.databinding.ActivityMainBinding
import com.neil.castellino.sports.fragments.HighlightsFragment
import com.neil.castellino.sports.fragments.PremiumFragment
import com.neil.castellino.sports.fragments.EventsFragment
import com.neil.castellino.sports.fragments.SearchPlayerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val eventsFragment = EventsFragment()
        val searchPlayer = SearchPlayerFragment()
        val highlightsFragment = HighlightsFragment()
        val premiumFragment = PremiumFragment()
        var activeFragment: Fragment = eventsFragment

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, premiumFragment, "4")
            .hide(premiumFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, highlightsFragment, "3")
            .hide(highlightsFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, searchPlayer, "2")
            .hide(searchPlayer).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, eventsFragment, "1")
            .commit()

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_scores -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(eventsFragment).commit()
                    activeFragment = eventsFragment
                }

                R.id.navigation_search_player->{
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(searchPlayer).commit()
                    activeFragment = searchPlayer
                }

                R.id.navigation_highlights -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(highlightsFragment).commit()
                    activeFragment = highlightsFragment
                }

                R.id.navigation_premium -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(premiumFragment).commit()
                    activeFragment = premiumFragment
                }
            }
            true
        }
    }
}