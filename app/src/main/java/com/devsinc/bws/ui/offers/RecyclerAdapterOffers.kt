package com.devsinc.bws.ui.offers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.model.Offer

class RecyclerAdapterOffers(private val offerList: List<Offer>) :
    RecyclerView.Adapter<RecyclerAdapterOffers.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_offers_item, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offerList[position])
    }

    override fun getItemCount() = offerList.size

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOfferName: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvExpiry: TextView = itemView.findViewById(R.id.tvExpiry)
        private val ivOffer: ImageView = itemView.findViewById(R.id.image)

        fun bind(offer: Offer) {
            tvOfferName.text = offer.name
            tvExpiry.text = offer.expiry_date
            Glide.with(ivOffer.context).load(offer.coupn_image)
                .into(ivOffer)
        }
    }
}