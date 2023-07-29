package com.neil.castellino.sports.network

import com.neil.castellino.sports.models.NewsData
import com.neil.castellino.sports.models.SportsData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("eventshighlights.php")
    fun getNews(): Call<NewsData>

    @GET("all_sports.php")
    fun getSportsList(): Call<SportsData>
}