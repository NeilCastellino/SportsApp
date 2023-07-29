package com.neil.castellino.sports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neil.castellino.sports.databinding.NewsRecyclerviewItemBinding
import com.neil.castellino.sports.models.Tvhighlight

class NewsAdapter(private val dataItems: List<Tvhighlight>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: NewsRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Tvhighlight){
            binding.data = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsRecyclerviewItemBinding.inflate(inflater, parent, false)
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