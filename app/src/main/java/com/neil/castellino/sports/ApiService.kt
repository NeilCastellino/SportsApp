package com.neil.castellino.sports

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("eventshighlights.php")
    fun getNews(): Call<News>
}