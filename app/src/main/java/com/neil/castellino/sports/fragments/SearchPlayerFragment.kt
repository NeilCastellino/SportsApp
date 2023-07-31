package com.neil.castellino.sports.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.neil.castellino.sports.MainViewModel
import com.neil.castellino.sports.R
import com.neil.castellino.sports.adapters.SearchPlayerAdapter
import com.neil.castellino.sports.databinding.FragmentSearchPlayerBinding

class SearchPlayerFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentSearchPlayerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_player, container, false)
        binding.lifecycleOwner = this

        val adapter = SearchPlayerAdapter()
        binding.searchPlayerRecyclerView.adapter = adapter
        binding.searchPlayerRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.fetchPlayerDetails(query)
                    FirebaseAnalytics.getInstance(binding.root.context)
                        .logEvent("search_player", Bundle().apply {
                            this.putString("query", query)
                        })
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.submitList(arrayListOf())
                    binding.helpText.visibility = View.VISIBLE
                }
                return false
            }
        })

        viewModel.playersList.observe(viewLifecycleOwner) { playersList ->
            if (playersList == null) {
                Toast.makeText(context, "No player information available", Toast.LENGTH_SHORT)
                    .show()
                adapter.submitList(arrayListOf())
                binding.helpText.visibility = View.VISIBLE
            } else {
                adapter.submitList(playersList)
                binding.helpText.visibility = View.INVISIBLE
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageLoadUrlPerson")
        fun loadImageUrl(view: ImageView, imageLoadUrlPerson: String?) {
            if (!imageLoadUrlPerson.isNullOrEmpty()) {
                Glide.with(view.context).load(imageLoadUrlPerson)
                    .placeholder(R.drawable.baseline_person).thumbnail(0.5f).into(view)
            }
        }
    }
}