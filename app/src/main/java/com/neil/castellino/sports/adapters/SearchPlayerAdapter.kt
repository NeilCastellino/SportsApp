package com.neil.castellino.sports.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neil.castellino.sports.databinding.PlayerRecyclerviewItemBinding
import com.neil.castellino.sports.models.Player

class SearchPlayerAdapter : ListAdapter<Player, SearchPlayerAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: PlayerRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Player) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.idPlayer == newItem.idPlayer
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlayerRecyclerviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}