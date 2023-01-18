package com.devsinc.bws.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.devsinc.bws.model.Customer

@Dao
interface CustomerDao {

    // Inserting customer
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    // Deleting customer
    @androidx.room.Query("DELETE FROM customer")
    suspend fun deleteCustomer()

    // Getting customer
    @androidx.room.Query("SELECT * FROM customer LIMIT 1")
    suspend fun getCustomer(): Customer?
}