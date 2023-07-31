package com.neil.castellino.sports.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neil.castellino.sports.MainViewModel
import com.neil.castellino.sports.R
import com.neil.castellino.sports.adapters.EventsAdapter
import com.neil.castellino.sports.databinding.FragmentEventsBinding
import com.neil.castellino.sports.models.Event
import com.neil.castellino.sports.models.OnRecyclerViewItemClickListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventsFragment : Fragment(), OnRecyclerViewItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentEventsBinding
    private var isEventsListDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (isEventsListDisplayed) {
                viewModel.fetchSportsList()
            } else {
                handleOnBackPressed()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false)
        binding.lifecycleOwner = this

        val adapter = EventsAdapter(this)
        binding.scoresRecyclerView.adapter = adapter
        binding.scoresRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.sportsList.observe(viewLifecycleOwner) { sportsList ->
            binding.scoresTitle.visibility = View.VISIBLE
            adapter.submitSportsList(sportsList)
            isEventsListDisplayed = false
        }

        viewModel.eventsList.observe(viewLifecycleOwner) { eventsList ->
            if (eventsList == null)
                Toast.makeText(context, "No events at this moment", Toast.LENGTH_SHORT).show()
            else {
                binding.scoresTitle.visibility = View.GONE
                adapter.submitEventsList(eventsList)
                isEventsListDisplayed = true
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageLoadUrl")
        fun loadImageUrl(view: ImageView, imageLoadUrl: String?) {
            if (!imageLoadUrl.isNullOrEmpty()) {
                Glide.with(view.context).load(imageLoadUrl).placeholder(R.drawable.baseline_tv)
                    .thumbnail(0.5f).into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("date")
        fun loadDate(view: TextView, date: String?) {
            if (!date.isNullOrEmpty()) {
                val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
                val outputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate: Date = inputFormatter.parse(date) ?: Date()
                view.text = outputFormatter.format(formattedDate)
            }
        }

        @JvmStatic
        @BindingAdapter("time")
        fun loadTime(view: TextView, time: String?) {
            if (!time.isNullOrEmpty()) {
                val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
                val outputFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val formattedDate: Date = inputFormatter.parse(time) ?: Date()
                view.text = outputFormatter.format(formattedDate)
            }
        }

        @JvmStatic
        @BindingAdapter("venue")
        fun loadVenue(view: TextView, venue: Event?) {
            if (venue != null) {
                val venueVal = if (venue.strVenue.isNullOrEmpty()) "" else venue.strVenue
                val cityVal = if (venue.strCity.isNullOrEmpty()) "" else venue.strCity
                val countryVal = if (venue.strCountry.isNullOrEmpty()) "" else venue.strCountry
                view.text = venueVal + " - " + cityVal + ", " + countryVal
            }
        }
    }

    override fun onItemClick(sport: String) {
        viewModel.fetchEventsList(sport)
    }
}