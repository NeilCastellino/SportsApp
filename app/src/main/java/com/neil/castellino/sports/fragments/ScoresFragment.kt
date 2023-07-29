package com.neil.castellino.sports.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neil.castellino.sports.R
import com.neil.castellino.sports.databinding.FragmentScoresBinding

class ScoresFragment : Fragment() {
    private lateinit var binding: FragmentScoresBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scores, container, false)

        return binding.root
    }
}