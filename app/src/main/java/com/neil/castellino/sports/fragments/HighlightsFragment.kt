package com.neil.castellino.sports.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neil.castellino.sports.MainViewModel
import com.neil.castellino.sports.adapters.HighlightsAdapter
import com.neil.castellino.sports.R
import com.neil.castellino.sports.databinding.FragmentHighlightsBinding
import java.util.regex.Pattern

class HighlightsFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentHighlightsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_highlights, container, false)
        binding.lifecycleOwner = this

        val adapter = HighlightsAdapter()
        binding.highlightsRecyclerView.adapter = adapter
        binding.highlightsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.highlightsList.observe(viewLifecycleOwner) { highlights ->
            adapter.submitList(highlights)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl", "videoUrl")
        fun loadImage(view: ImageView, imageUrl: String?, videoUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context).load(imageUrl).placeholder(R.drawable.baseline_tv)
                    .thumbnail(0.5f).into(view)
            } else if (!videoUrl.isNullOrEmpty()) {
                val videoId = getYoutubeVideoId(videoUrl)
                val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"
                Glide.with(view.context).load(thumbnailUrl).into(view)
            }
        }

        private fun getYoutubeVideoId(youtubeUrl: String): String {
            val pattern =
                "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%\u200C\u200B2F|%2Fv%2F)[^#\\&\\?\\n]*"
            val compiledPattern = Pattern.compile(pattern)
            val matcher = compiledPattern.matcher(youtubeUrl)
            return if (matcher.find()) {
                matcher.group()
            } else {
                ""
            }
        }
    }
}