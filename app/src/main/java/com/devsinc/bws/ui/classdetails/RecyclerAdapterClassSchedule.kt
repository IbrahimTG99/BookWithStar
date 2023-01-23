package com.devsinc.bws.ui.classdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsinc.bws.R
import com.devsinc.bws.model.Schedule

class RecyclerAdapterClassSchedule(private val classSchedule: List<Schedule>) :
    RecyclerView.Adapter<RecyclerAdapterClassSchedule.ClassScheduleViewHolder>() {

    class ClassScheduleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tv_day)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_slot)
        val tvScheduleName: TextView = itemView.findViewById(R.id.tv_schedule_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassScheduleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ClassScheduleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClassScheduleViewHolder, position: Int) {
        val currentSchedule = classSchedule[position]
        holder.tvDay.text = currentSchedule.schedule_day
        holder.tvTime.text = currentSchedule.schedule_time
        holder.tvScheduleName.text = currentSchedule.schedule_batch
    }

    override fun getItemCount() = classSchedule.size
}