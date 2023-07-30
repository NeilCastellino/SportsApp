package com.neil.castellino.sports.network

import com.neil.castellino.sports.models.HighlightsData
import com.neil.castellino.sports.models.SportsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager {

    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    fun getPosts(callback: ApiCallback<HighlightsData>) {
        val call = apiService.getHighlights()
        call.enqueue(object : Callback<HighlightsData> {
            override fun onResponse(call: Call<HighlightsData>, response: Response<HighlightsData>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        callback.onSuccess(posts)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody ?: "Failed to fetch posts."
                    callback.onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<HighlightsData>, t: Throwable) {
                callback.onFailure("Network error: ${t.message}")
            }
        })
    }

    fun getSportsList(callback: ApiCallback<SportsData>) {
        val call = apiService.getSportsList()
        call.enqueue(object : Callback<SportsData> {
            override fun onResponse(call: Call<SportsData>, response: Response<SportsData>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        callback.onSuccess(posts)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody ?: "Failed to fetch posts."
                    callback.onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<SportsData>, t: Throwable) {
                callback.onFailure("Network error: ${t.message}")
            }
        })
    }
}

