package com.neil.castellino.sports.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neil.castellino.sports.R
import com.neil.castellino.sports.adapters.SportsListAdapter
import com.neil.castellino.sports.databinding.FragmentScoresBinding
import com.neil.castellino.sports.models.SportsData
import com.neil.castellino.sports.network.ApiCallback
import com.neil.castellino.sports.network.ApiManager

class ScoresFragment : Fragment() {
    private lateinit var binding: FragmentScoresBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scores, container, false)

        binding.scoresRecyclerView.adapter = SportsListAdapter(listOf())
        binding.scoresRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val apiManager = ApiManager()
        apiManager.getSportsList(object : ApiCallback<SportsData> {
            override fun onSuccess(response: SportsData) {
                binding.scoresRecyclerView.adapter = SportsListAdapter(response.sports)
            }

            override fun onFailure(errorMessage: String) {
                Toast.makeText(requireContext(), "API Error: $errorMessage", Toast.LENGTH_LONG)
                    .show()
                Log.e("API Error getPosts", errorMessage)
            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context).load(imageUrl).thumbnail(0.5f).into(view)
            }
        }
    }
}