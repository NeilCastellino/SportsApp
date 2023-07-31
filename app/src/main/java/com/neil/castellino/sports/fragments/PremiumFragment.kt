package com.neil.castellino.sports.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.neil.castellino.sports.R
import com.neil.castellino.sports.databinding.FragmentPremiumBinding
import com.neil.castellino.sports.models.Premium

class PremiumFragment : Fragment() {
    private lateinit var binding: FragmentPremiumBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_premium, container, false)

        val monthlyPremium = Premium(
            "Try Premium for 1 month",
            true,
            "Free for 1 month, then \$14.99 per month after."
        )

        val yearlyPremium = Premium(
            "Try Premium for 3 months",
            false,
            "Free for 3 months, then \$9.99 per month after."
        )

        binding.data = yearlyPremium
        binding.customSwitch.setOnCheckedChangeListener { _, isYearly ->
            if (isYearly) {
                binding.data = yearlyPremium
            } else {
                binding.data = monthlyPremium
            }
        }
        binding.premiumButton.setOnClickListener {
            FirebaseAnalytics.getInstance(requireContext())
                .logEvent("button_click", Bundle().apply {
                    this.putString("name", "Get Premium")
                })
        }

        return binding.root
    }
}