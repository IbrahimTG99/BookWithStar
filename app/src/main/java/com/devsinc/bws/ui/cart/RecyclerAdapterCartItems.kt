package com.devsinc.bws.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsinc.bws.R
import com.devsinc.bws.model.TimeSlot
import com.devsinc.bws.model.TimeSlotCart

class RecyclerAdapterCartItems (private val cartItems: MutableList<TimeSlotCart>) : RecyclerView.Adapter<RecyclerAdapterCartItems.CartItemViewHolder>() {

    var itemClickListener: ((item: TimeSlotCart) -> Unit)? = null

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_court_name)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvTimeSlot: TextView = itemView.findViewById(R.id.tv_time_slot)
        private val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete)

        var itemCLick: ((item: TimeSlotCart) -> Unit)? = null

        fun bind(cartItem: TimeSlotCart) {
            tvTitle.text = cartItem.court_name
            tvPrice.text = itemView.context.getString(R.string.Pricing_modifier_2, cartItem.slot_price.toString())
            tvDate.text = cartItem.slot_date
            tvTimeSlot.text = cartItem.time_slot
            ivDelete.setOnClickListener {
                cartItems.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                itemCLick?.invoke(cartItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterCartItems.CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_cart_item, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(cartItems[position])
        holder.itemCLick = itemClickListener
    }

    override fun getItemCount() = cartItems.size
}