package com.devsinc.bws.ui.bookonline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsinc.bws.R
import com.devsinc.bws.model.Court
import com.devsinc.bws.ui.home.RecyclerAdapterFeaturedClasses

class RecyclerAdapterCourts(private val courtList: List<Court>) :
    RecyclerView.Adapter<RecyclerAdapterCourts.CourtViewHolder>() {

    class CourtViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvCourtName: TextView = itemView.findViewById(R.id.tvCourtName)
        val cvCourtItem: View = itemView.findViewById(R.id.cvCourtItem)

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
    var selected: Int = -1

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_court_item, parent, false)
        return CourtViewHolder(itemView, this.listener)
    }

    override fun onBindViewHolder(holder: CourtViewHolder, position: Int) {
        val currentCourt = courtList[position]
        holder.tvCourtName.text = currentCourt.court_name
        if (selected == position) {
            holder.cvCourtItem.setBackgroundResource(R.drawable.gradient)
        } else {
            holder.cvCourtItem.setBackgroundResource(R.drawable.default_bg)
        }
    }

    override fun getItemCount() = courtList.size
}