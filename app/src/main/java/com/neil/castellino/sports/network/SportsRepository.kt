package com.neil.castellino.sports.network

import android.util.Log
import com.neil.castellino.sports.models.Event
import com.neil.castellino.sports.models.Sport
import com.neil.castellino.sports.models.Tvhighlight
import com.neil.castellino.sports.network.RetrofitClient.apiService
import java.lang.Exception

class SportsRepository {

    suspend fun getHighlightsList(): List<Tvhighlight> {
        return try {
            apiService.getHighlightsList().tvhighlights
        } catch (e: Exception) {
            Log.e("Highlights API Error:", e.message.toString())
            emptyList()
        }
    }

    suspend fun getSportsList(): List<Sport> {
        return try {
            apiService.getSportsList().sports
        } catch (e: Exception) {
            Log.e("SportsList API Error:", e.message.toString())
            emptyList()
        }
    }

    suspend fun getEventsList(date: String, sport: String): List<Event> {
        return try {
            apiService.getEventsList(date, sport).events
        } catch (e: Exception) {
            Log.e("SportsList API Error:", e.message.toString())
            emptyList()
        }
    }
}

