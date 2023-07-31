package com.neil.castellino.sports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.neil.castellino.sports.databinding.ActivityMainBinding
import com.neil.castellino.sports.fragments.HighlightsFragment
import com.neil.castellino.sports.fragments.PremiumFragment
import com.neil.castellino.sports.fragments.EventsFragment
import com.neil.castellino.sports.fragments.SearchPlayerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var timer: CountDownTimer
    private final var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics

        // Initialize Ads
        MobileAds.initialize(this)
        loadBannerAd()
        loadInterstitialAd()
        startTimer()

        // Setup Fragments
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

                R.id.navigation_search_player -> {
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

    private fun loadBannerAd() {
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun loadInterstitialAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
            startTimer()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                showInterstitialAd()
            }
        }

        timer.start()
    }
}