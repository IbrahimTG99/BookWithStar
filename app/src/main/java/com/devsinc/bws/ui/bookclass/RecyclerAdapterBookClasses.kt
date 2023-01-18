package com.devsinc.bws.ui.bookclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsinc.bws.R
import com.devsinc.bws.model.BookClassItem

class RecyclerAdapterBookClasses(private val bookClasses: List<BookClassItem>) :
    RecyclerView.Adapter<RecyclerAdapterBookClasses.BookClassViewHolder>() {
    class BookClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvDiscount: TextView = itemView.findViewById(R.id.tvDiscount)
        private val tvSessions: TextView = itemView.findViewById(R.id.tvSessions)
        private val linearSession: LinearLayout = itemView.findViewById(R.id.linearSessions)


        fun bind(bookClass: BookClassItem) {
            tvTitle.text = bookClass.package_name
            tvDiscount.text = bookClass.features[0]
            if (bookClass.features.size > 1) {
                tvSessions.text = bookClass.features[1]
            } else {
                linearSession.visibility = View.GONE
            }

            tvPrice.text = itemView.context.getString(
                R.string.Pricing_modifier,
                bookClass.package_price,
                bookClass.frequency_name
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookClassViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_book_class_item, parent, false)
        return BookClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookClassViewHolder, position: Int) {
        holder.bind(bookClasses[position])
    }

    override fun getItemCount() = bookClasses.size
}