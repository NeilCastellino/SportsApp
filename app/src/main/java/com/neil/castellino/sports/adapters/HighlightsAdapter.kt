package com.neil.castellino.sports.adapters

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neil.castellino.sports.databinding.HighlightsRecyclerviewItemBinding
import com.neil.castellino.sports.models.Tvhighlight

class HighlightsAdapter() :
    ListAdapter<Tvhighlight, HighlightsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: HighlightsRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Tvhighlight) {
            binding.data = data
            binding.root.setOnClickListener {
                openYouTubeVideo(data.strVideo, binding)
            }

            binding.executePendingBindings()
        }
    }

    private fun openYouTubeVideo(youtubeUrl: String, binding: HighlightsRecyclerviewItemBinding) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        intentApp.setPackage("com.google.android.youtube")
        val context = binding.root.context

        try {
            context.startActivity(intentApp)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(intentBrowser)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Tvhighlight>() {
        override fun areItemsTheSame(oldItem: Tvhighlight, newItem: Tvhighlight): Boolean {
            return oldItem.idEvent == newItem.idEvent
        }

        override fun areContentsTheSame(oldItem: Tvhighlight, newItem: Tvhighlight): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HighlightsRecyclerviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}