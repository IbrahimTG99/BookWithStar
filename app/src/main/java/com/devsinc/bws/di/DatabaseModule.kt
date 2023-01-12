package com.devsinc.bws.di

import android.content.Context
import androidx.room.Room
import com.devsinc.bws.data.AppDb
import com.devsinc.bws.data.CustomerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideCustomerDao(appDb: AppDb): CustomerDao {
        return appDb.customerDao()
    }

    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(
            context,
            AppDb::class.java,
            "app_db"
        ).build()
    }
}