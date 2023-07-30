package com.neil.castellino.sports.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neil.castellino.sports.databinding.SportsListRecyclerviewItemBinding
import com.neil.castellino.sports.models.Sport

class SportsListAdapter(private val dataItems: List<Sport>) :
    RecyclerView.Adapter<SportsListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SportsListRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Sport) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SportsListRecyclerviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }
}