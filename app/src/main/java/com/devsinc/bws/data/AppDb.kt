package com.devsinc.bws.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devsinc.bws.model.Customer

@Database(entities = [Customer::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
}