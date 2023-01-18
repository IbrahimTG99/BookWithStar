package com.devsinc.bws.ui.findvenue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.model.Venue

class RecyclerAdapterVenues(private val venueList: List<Venue>) :
    RecyclerView.Adapter<RecyclerAdapterVenues.VenueViewHolder>() {

    class VenueViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val ivVenueImage: ImageView = itemView.findViewById<ImageView>(R.id.iv_venue_image)
        val tvVenueName: TextView = itemView.findViewById<TextView>(R.id.tv_venue_name)
        val tvVenueLocation: TextView = itemView.findViewById<TextView>(R.id.tvLocation)
        val tvVenueDistance: TextView = itemView.findViewById<TextView>(R.id.tvDistance)
        val linearLayout: LinearLayout = itemView.findViewById<LinearLayout>(R.id.linearIcons)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterVenues.VenueViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_find_venue_item, parent, false)
        return RecyclerAdapterVenues.VenueViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterVenues.VenueViewHolder, position: Int) {
        val currentVenue = venueList[position]
        holder.tvVenueName.text = currentVenue.venue_name
        holder.tvVenueLocation.text = currentVenue.venue_location
        holder.tvVenueDistance.text = currentVenue.distance
        Glide.with(holder.ivVenueImage.context).load(currentVenue.venue_image)
            .into(holder.ivVenueImage)

        holder.linearLayout.removeAllViews()
        for (i in 0 until currentVenue.venue_icon.size) {
            val imageView = ImageView(holder.linearLayout.context)
            imageView.layoutParams = LinearLayout.LayoutParams(100, 100)
            Glide.with(holder.linearLayout.context).load(currentVenue.venue_icon[i])
                .into(imageView)
            holder.linearLayout.addView(imageView)
        }
    }

    override fun getItemCount() = venueList.size
}