package com.neil.castellino.sports.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neil.castellino.sports.databinding.SportsListRecyclerviewItemBinding
import com.neil.castellino.sports.models.Sport

class SportsListAdapter() :
    ListAdapter<Sport, SportsListAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: SportsListRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Sport) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Sport>() {
        override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
            return oldItem.idSport == newItem.idSport
        }

        override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SportsListRecyclerviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}