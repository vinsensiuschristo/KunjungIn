package com.bangkit.kunjungin.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.kunjungin.data.remote.response.TopRatedPlaceRecommendation
import com.bangkit.kunjungin.databinding.ItemRowDestination2Binding
import com.bangkit.kunjungin.ui.detail.DetailActivity
import com.squareup.picasso.Picasso

class TopRatedPlaceAdapter : ListAdapter<TopRatedPlaceRecommendation, TopRatedPlaceAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowDestination2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val topRatedPlaceItem = getItem(position)
        holder.bind(topRatedPlaceItem)
    }

    class ListViewHolder(private val binding: ItemRowDestination2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(topRatedPlacesItem: TopRatedPlaceRecommendation) {
            binding.apply {
                locationName.text = topRatedPlacesItem.name
                rating.text = topRatedPlacesItem.rating.toString()
                val apiKey = "AIzaSyDsoEFR73xhtFhtlzBaXaXKLybnH9gp8BI"
                val maxWidth = 400
                val photoUrl = "https://maps.googleapis.com/maps/api/place/photo" +
                        "?maxwidth=$maxWidth" +
                        "&photo_reference=${topRatedPlacesItem.photoReference}" +
                        "&key=$apiKey"

                Picasso.get().load(photoUrl).fit().into(destinationImage)
            }

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DESTINATION_ID, topRatedPlacesItem.id.toInt())
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        const val DESTINATION_ID = "destination_id"
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopRatedPlaceRecommendation>() {
            override fun areItemsTheSame(oldItem: TopRatedPlaceRecommendation, newItem: TopRatedPlaceRecommendation): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: TopRatedPlaceRecommendation,
                newItem: TopRatedPlaceRecommendation,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}