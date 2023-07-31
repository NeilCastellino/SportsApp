package com.neil.castellino.sports.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neil.castellino.sports.MainViewModel
import com.neil.castellino.sports.OnSportsItemClickListener
import com.neil.castellino.sports.R
import com.neil.castellino.sports.adapters.SportsListAdapter
import com.neil.castellino.sports.databinding.FragmentScoresBinding

class ScoresFragment : Fragment(), OnSportsItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentScoresBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scores, container, false)
        binding.lifecycleOwner = this

        val adapter = SportsListAdapter(this)
        binding.scoresRecyclerView.adapter = adapter
        binding.scoresRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.sportsList.observe(viewLifecycleOwner) { sportsList ->
            adapter.submitSportsList(sportsList)
        }

        viewModel.eventsList.observe(viewLifecycleOwner) { eventsList ->
            if (eventsList == null)
                Toast.makeText(context, "No events at this moment", Toast.LENGTH_SHORT).show()
            else {
                binding.scoresTitle.visibility = View.GONE
                adapter.submitEventsList(eventsList)
            }
        }

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

    override fun onItemClick(sport: String) {
        viewModel.fetchEventsList(sport)
    }
}