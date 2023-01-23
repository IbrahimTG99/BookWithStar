package com.devsinc.bws.ui.classdetails

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

class RecyclerAdapterClassDetails(private val classDetails: List<DetailsData>) :
    RecyclerView.Adapter<RecyclerAdapterClassDetails.ClassDetailsViewHolder>() {
    class ClassDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(classDetail: DetailsData) {
            when (classDetail) {
                is DetailsData.Info -> {
                    val tvDetail = itemView.findViewById<TextView>(R.id.tv_detail)
                    tvDetail.text = classDetail.title
                    val ivDetail = itemView.findViewById<ImageView>(R.id.iv_icon)
                    if (classDetail.isOnline) {
                        Glide.with(ivDetail.context).load(classDetail.icon).into(ivDetail)
                    } else {
                        ivDetail.setImageResource(classDetail.icon.toInt())
                    }
                }
                is DetailsData.Heading -> {
                    val tvHeading = itemView.findViewById<TextView>(R.id.tv_heading)
                    tvHeading.text = classDetail.title
                }
            }
        }
    }

    companion object {
        const val INFO = 1
        const val HEADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassDetailsViewHolder {
        val view = when (viewType) {
            INFO -> {
                LayoutInflater.from(parent.context).inflate(R.layout.rv_detail_item, parent, false)
            }
            HEADING -> {
                LayoutInflater.from(parent.context).inflate(R.layout.rv_heading_item, parent, false)
            }
            else -> {
                Log.d("RecyclerAdapterClass", viewType.toString())
                throw IllegalArgumentException("Invalid type")
            }
        }
        return ClassDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassDetailsViewHolder, position: Int) {
        holder.bind(classDetails[position])
    }

    override fun getItemCount(): Int {
        return classDetails.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (classDetails[position]) {
            is DetailsData.Info -> INFO
            is DetailsData.Heading -> HEADING
        }
    }

}