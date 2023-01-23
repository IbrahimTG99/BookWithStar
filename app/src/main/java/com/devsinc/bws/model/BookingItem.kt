package com.devsinc.bws.model

data class BookingItem(
    val amount_paid: Int,
    val booking_status: String,
    val bookingstart_date: String,
    val customer_id: Int,
    val invoice_id: String,
    val invoice_type: Int,
    val order_id: String,
    val total_amount: Int,
    val total_due: Int,
    val view_invoice: String
)