package com.neil.castellino.sports

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager {

    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    fun getPosts(callback: ApiCallback<News>) {
        val call = apiService.getNews()
        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        callback.onSuccess(posts)
                    }
                } else {
                    callback.onFailure("Failed to fetch posts.")
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                callback.onFailure("Network error: ${t.message}")
            }
        })
    }
}

