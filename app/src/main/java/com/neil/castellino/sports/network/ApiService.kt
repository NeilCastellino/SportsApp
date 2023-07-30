package com.neil.castellino.sports.network

import com.neil.castellino.sports.models.EventsData
import com.neil.castellino.sports.models.HighlightsData
import com.neil.castellino.sports.models.SportsData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("eventshighlights.php")
    suspend fun getHighlightsList(): HighlightsData

    @GET("all_sports.php")
    suspend fun getSportsList(): SportsData

    @GET("eventsday.php")
    suspend fun getEventsList(@Query("d") date: String): EventsData
}