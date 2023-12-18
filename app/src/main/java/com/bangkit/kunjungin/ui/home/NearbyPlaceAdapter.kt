package com.bangkit.kunjungin.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.kunjungin.data.remote.response.NearbyPlaceRecommendation
import com.bangkit.kunjungin.databinding.ItemRowDestinationBinding
import com.squareup.picasso.Picasso

class NearbyPlaceAdapter : ListAdapter<NearbyPlaceRecommendation, NearbyPlaceAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val nearbyPlaceItem = getItem(position)
        holder.bind(nearbyPlaceItem)
    }

    class ListViewHolder(private val binding: ItemRowDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nearbyPlacesItem: NearbyPlaceRecommendation) {
            binding.apply {
                val distanceText = simplifyDistance(nearbyPlacesItem.distance)
                distanceToLocation.text = distanceText
                locationName.text = nearbyPlacesItem.name

                val apiKey = "AIzaSyDsoEFR73xhtFhtlzBaXaXKLybnH9gp8BI"
                val maxWidth = 400
                val photoUrl = "https://maps.googleapis.com/maps/api/place/photo" +
                        "?maxwidth=$maxWidth" +
                        "&photo_reference=${nearbyPlacesItem.photoReference}" +
                        "&key=$apiKey"

                Picasso.get().load(photoUrl).fit().into(destinationImage)
            }

            itemView.setOnClickListener {
//                val context = itemView.context
////                val intent = Intent(context, DetailActivity::class.java)
////                intent.putExtra("storyItem", storyItem)
////                val optionsCompat: ActivityOptionsCompat =
////                    ActivityOptionsCompat.makeSceneTransitionAnimation(
////                        itemView.context as Activity,
////                        Pair(binding.storyImage, "profile"),
////                        Pair(binding.username, "name"),
////                        Pair(binding.description, "description"),
////                    )
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }

        private fun simplifyDistance(distance: Double): String {
            val roundedDistance = distance.toInt()
            return if (distance % 1 == 0.0) {
                "$roundedDistance km"
            } else {
                String.format("%.2f km", distance)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NearbyPlaceRecommendation>() {
            override fun areItemsTheSame(oldItem: NearbyPlaceRecommendation, newItem: NearbyPlaceRecommendation): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: NearbyPlaceRecommendation,
                newItem: NearbyPlaceRecommendation,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}