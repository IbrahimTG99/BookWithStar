package com.devsinc.bws.ui.stadiumdetails

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.model.DetailsData

class RecyclerAdapterStadiumDetails(private val stadiumDetails: List<DetailsData>) :
    RecyclerView.Adapter<RecyclerAdapterStadiumDetails.StadiumDetailsViewHolder>() {

    class StadiumDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(stadiumDetail: DetailsData) {
            when (stadiumDetail) {
                is DetailsData.Info -> {
                    val tvDetail = itemView.findViewById<TextView>(R.id.tv_detail)
                    tvDetail.text = stadiumDetail.title
                    val ivDetail = itemView.findViewById<ImageView>(R.id.iv_icon)
                    if (stadiumDetail.isOnline) {
                        Glide.with(ivDetail.context).load(stadiumDetail.icon).into(ivDetail)
                    } else {
                        ivDetail.setImageResource(stadiumDetail.icon.toInt())
                    }
                }
                is DetailsData.Heading -> {
                    val tvHeading = itemView.findViewById<TextView>(R.id.tv_heading)
                    tvHeading.text = stadiumDetail.title
                }
            }
        }
    }

    companion object {
        const val INFO = 1
        const val HEADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StadiumDetailsViewHolder {
        val view = when (viewType) {
            INFO -> {
                LayoutInflater.from(parent.context).inflate(R.layout.rv_detail_item, parent, false)
            }
            HEADING -> {
                LayoutInflater.from(parent.context).inflate(R.layout.rv_heading_item, parent, false)
            }
            else -> {
                Log.d("RecyclerAdapterStadium", viewType.toString())
                throw IllegalArgumentException("Invalid type")
            }
        }
        return StadiumDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StadiumDetailsViewHolder, position: Int) {
        holder.bind(stadiumDetails[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when (stadiumDetails[position]) {
            is DetailsData.Info -> INFO
            is DetailsData.Heading -> HEADING
        }
    }

    override fun getItemCount() = stadiumDetails.size
}