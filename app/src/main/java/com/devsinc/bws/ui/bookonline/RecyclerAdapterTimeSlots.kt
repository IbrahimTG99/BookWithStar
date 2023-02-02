package com.devsinc.bws.ui.bookonline

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsinc.bws.R
import com.devsinc.bws.model.TimeSlot

class RecyclerAdapterTimeSlots(private val timeSlotList: List<TimeSlot>) :
    RecyclerView.Adapter<RecyclerAdapterTimeSlots.TimeSlotViewHolder>() {

    class TimeSlotViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvTimeSlot: TextView = itemView.findViewById(R.id.tv_time_slot)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val cardTimeSlot: View = itemView.findViewById(R.id.card_time_slot)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_time_slots, parent, false)
        return TimeSlotViewHolder(itemView, this.listener)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val currentTimeSlot = timeSlotList[position]
        holder.tvTimeSlot.text = currentTimeSlot.time_slot
        holder.tvPrice.text = holder.tvPrice.context.getString(
            R.string.Pricing_modifier_2,
            currentTimeSlot.slot_price.toString()
        )
        holder.cardTimeSlot.isClickable = false
        when(currentTimeSlot.time_slot_status) {
            1 -> {
                // In cart
                holder.cardTimeSlot.setBackgroundResource(R.drawable.bg_disabled)
                holder.tvPrice.setTextColor(holder.tvPrice.context.getColor(R.color.white))
                holder.tvPrice.text = holder.tvPrice.context.getString(R.string.in_cart)
            }
            2 -> {
                // Booked
                holder.cardTimeSlot.setBackgroundResource(R.drawable.bg_disabled)
                holder.tvPrice.setTextColor(holder.tvPrice.context.getColor(R.color.white))
                holder.tvPrice.text = holder.tvPrice.context.getString(R.string.booked)
            }
            3 -> {
                // Not Available
                holder.cardTimeSlot.setBackgroundResource(R.drawable.bg_disabled)
                holder.tvPrice.setTextColor(holder.tvPrice.context.getColor(R.color.white))
                holder.tvPrice.text = holder.tvPrice.context.getString(R.string.not_available)
            }
            4 -> {
                // Group
                holder.cardTimeSlot.setBackgroundResource(R.drawable.bg_disabled)
                holder.tvPrice.setTextColor(holder.tvPrice.context.getColor(R.color.white))
                holder.tvPrice.text = holder.tvPrice.context.getString(R.string.group)
            }
            5 -> {
                // Reserved
                holder.cardTimeSlot.setBackgroundResource(R.drawable.bg_disabled)
                holder.tvPrice.setTextColor(holder.tvPrice.context.getColor(R.color.white))
                holder.tvPrice.text = holder.tvPrice.context.getString(R.string.reserved)
            }
            else -> {
                holder.cardTimeSlot.isClickable = true
            }
        }
    }

    override fun getItemCount() = timeSlotList.size
}