package com.devsinc.bws.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.model.FeaturedVenue

class RecyclerAdapterFeaturedVenues(private val featuredVenues: List<FeaturedVenue>) :
    RecyclerView.Adapter<RecyclerAdapterFeaturedVenues.VenueViewHolder>() {

    class VenueViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val ivBookVenue: ImageView = itemView.findViewById<ImageView>(R.id.iv_book_venue)
        val tvBookVenueTitle: TextView = itemView.findViewById<TextView>(R.id.tv_book_venue_title)
        val iCallIcon: ImageView = itemView.findViewById<ImageView>(R.id.iv_call_icon)
        val tvLocation: TextView = itemView.findViewById<TextView>(R.id.tv_location)
        val rBar: RatingBar = itemView.findViewById<RatingBar>(R.id.r_bar)


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_featured_item, parent, false)
        return VenueViewHolder(itemView, this.listener)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val currentVenue = featuredVenues[position]
        holder.tvBookVenueTitle.text = currentVenue.venue_name
        holder.tvLocation.text = currentVenue.venue_location
        holder.rBar.rating = currentVenue.venue_rating
        Glide.with(holder.ivBookVenue.context).load(currentVenue.venue_image)
            .into(holder.ivBookVenue)
    }

    override fun getItemCount() = featuredVenues.size
}