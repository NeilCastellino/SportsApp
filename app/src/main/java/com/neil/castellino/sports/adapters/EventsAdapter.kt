package com.neil.castellino.sports.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neil.castellino.sports.databinding.EventsRecyclerviewItemBinding
import com.neil.castellino.sports.models.Event

class EventsAdapter() :
    ListAdapter<Event, EventsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: EventsRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Event) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.idEvent == newItem.idEvent
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventsRecyclerviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}