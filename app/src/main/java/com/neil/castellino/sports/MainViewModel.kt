package com.neil.castellino.sports

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neil.castellino.sports.models.Event
import com.neil.castellino.sports.models.Sport
import com.neil.castellino.sports.models.Tvhighlight
import com.neil.castellino.sports.network.SportsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {
    private val repository = SportsRepository()

    private val _highlightsList = MutableLiveData<List<Tvhighlight>>()
    private val _sportsList = MutableLiveData<List<Sport>>()
    private val _eventsList = MutableLiveData<List<Event>>()

    val highlightsList: LiveData<List<Tvhighlight>>
        get() = _highlightsList
    val sportsList: LiveData<List<Sport>>
        get() = _sportsList
    val eventsList: LiveData<List<Event>>
        get() = _eventsList

    init {
        fetchSportsList()
        fetchHighlightsList()
    }

    private fun fetchHighlightsList() {
        viewModelScope.launch(Dispatchers.IO) {
            val highlightsList = repository.getHighlightsList()
            withContext(Dispatchers.Main) {
                _highlightsList.value = highlightsList
            }
        }
    }

    private fun fetchSportsList() {
        viewModelScope.launch(Dispatchers.IO) {
            val sportsList = repository.getSportsList()
            withContext(Dispatchers.Main) {
                _sportsList.value = sportsList
            }
        }
    }

    fun fetchEventsList(sport: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val eventsList = repository.getEventsList(getTodayDate(), getSport(sport))
            withContext(Dispatchers.Main) {
                Log.i("fetchEventsList", "Values received: $eventsList")
                _eventsList.value = eventsList
            }
        }
    }

    private fun getTodayDate(): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        Log.i("getTodayDate", dateFormatter.format(Date()))
        return dateFormatter.format(Date())
    }

    private fun getSport(sport: String): String {
        return sport.replace(" ", "")
    }
}