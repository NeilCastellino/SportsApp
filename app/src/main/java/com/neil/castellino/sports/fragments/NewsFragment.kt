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
import com.neil.castellino.sports.NewsAdapter
import com.neil.castellino.sports.network.ApiCallback
import com.neil.castellino.sports.network.ApiManager
import com.neil.castellino.sports.models.NewsData
import com.neil.castellino.sports.R
import com.neil.castellino.sports.databinding.FragmentNewsBinding
import java.util.regex.Pattern

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        binding.newsRecyclerView.adapter = NewsAdapter(listOf())
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val apiManager = ApiManager()
        apiManager.getPosts(object : ApiCallback<NewsData> {
            override fun onSuccess(response: NewsData) {
                binding.newsRecyclerView.adapter = NewsAdapter(response.tvhighlights)
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
        @BindingAdapter("imageUrl", "videoUrl")
        fun loadImage(view: ImageView, imageUrl: String?, videoUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context).load(imageUrl).thumbnail(0.5f).into(view)
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