package com.neil.castellino.sports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neil.castellino.sports.models.Sport
import com.neil.castellino.sports.models.Tvhighlight
import com.neil.castellino.sports.network.SportsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val repository = SportsRepository()

    private val _highlights = MutableLiveData<List<Tvhighlight>>()
    private val _sportsList = MutableLiveData<List<Sport>>()

    val highlights: LiveData<List<Tvhighlight>>
        get() = _highlights
    val sportsList: LiveData<List<Sport>>
        get() = _sportsList

    init {
        fetchHighlights()
        fetchSportsList()
    }

    private fun fetchHighlights() {
        viewModelScope.launch(Dispatchers.IO) {
            val highlights = repository.getHighlights()
            withContext(Dispatchers.Main) {
                _highlights.value = highlights
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
}