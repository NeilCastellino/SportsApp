package com.neil.castellino.sports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neil.castellino.sports.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        val apiManager = ApiManager()
        apiManager.getPosts(object : ApiCallback<News> {
            override fun onSuccess(response: News) {
                binding.newsText.text = response.toString()
            }

            override fun onFailure(errorMessage: String) {
                binding.newsText.text = errorMessage
            }
        })

        return binding.root
    }
}