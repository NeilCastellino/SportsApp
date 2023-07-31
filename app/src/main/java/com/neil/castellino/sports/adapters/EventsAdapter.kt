package com.neil.castellino.sports.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neil.castellino.sports.models.OnSportsItemClickListener
import com.neil.castellino.sports.databinding.EventsRecyclerviewItemBinding
import com.neil.castellino.sports.databinding.SportsListRecyclerviewItemBinding
import com.neil.castellino.sports.models.Event
import com.neil.castellino.sports.models.Sport

class EventsAdapter(private val listener: OnSportsItemClickListener) :
    ListAdapter<BaseEventsData, RecyclerView.ViewHolder>(DiffCallback()) {

    inner class SportsViewHolder(private val binding: SportsListRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Sport) {
            binding.data = data
            binding.root.setOnClickListener {
                listener.onItemClick(data.strSport)
            }
            binding.executePendingBindings()
        }
    }

    inner class EventsViewHolder(private val binding: EventsRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Event) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    fun submitSportsList(sportsList: List<Sport>) {
        val sportsData = sportsList.map { SportWrapper(it) }
        submitList(sportsData)
    }

    // Method to submit a list of Event data to the adapter
    fun submitEventsList(eventsList: List<Event>) {
        val eventsData = eventsList.map { EventWrapper(it) }
        submitList(eventsData)
    }

    class DiffCallback : DiffUtil.ItemCallback<BaseEventsData>() {
        override fun areItemsTheSame(oldItem: BaseEventsData, newItem: BaseEventsData): Boolean {
            return when {
                oldItem is SportWrapper && newItem is SportWrapper ->
                    oldItem.sport.idSport == newItem.sport.idSport

                oldItem is EventWrapper && newItem is EventWrapper ->
                    oldItem.event.idEvent == newItem.event.idEvent

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: BaseEventsData, newItem: BaseEventsData): Boolean {
            return when {
                oldItem is SportWrapper && newItem is SportWrapper ->
                    oldItem.sport.idSport == newItem.sport.idSport

                oldItem is EventWrapper && newItem is EventWrapper ->
                    oldItem.event.idEvent == newItem.event.idEvent

                else -> false
            }
        }

        override fun getChangePayload(oldItem: BaseEventsData, newItem: BaseEventsData): Any? {
            // Check if view holder type has changed and return the payload if needed
            return if (oldItem::class != newItem::class) {
                Any() // Return any non-null payload to indicate a change in view holder type
            } else {
                super.getChangePayload(oldItem, newItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            SPORTS_VIEW -> SportsViewHolder(
                SportsListRecyclerviewItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            EVENTS_VIEW -> EventsViewHolder(
                EventsRecyclerviewItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Unknown view holder type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is SportsViewHolder -> {
                val data = (item as SportWrapper).sport
                holder.bind(data)
            }

            is EventsViewHolder -> {
                val data = (item as EventWrapper).event
                holder.bind(data)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SportWrapper -> SPORTS_VIEW
            is EventWrapper -> EVENTS_VIEW
            else -> throw IllegalArgumentException("Unknown view holder type")
        }
    }

    companion object {
        private const val SPORTS_VIEW = 1
        private const val EVENTS_VIEW = 2
    }
}

interface BaseEventsData
data class SportWrapper(val sport: Sport) : BaseEventsData
data class EventWrapper(val event: Event) : BaseEventsData