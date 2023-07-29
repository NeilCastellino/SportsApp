package com.neil.castellino.sports.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neil.castellino.sports.R
import com.neil.castellino.sports.databinding.FragmentPremiumBinding

class PremiumFragment : Fragment() {
    private lateinit var binding: FragmentPremiumBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_premium, container, false)

        return binding.root
    }
}