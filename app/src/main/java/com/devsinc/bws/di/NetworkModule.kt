package com.devsinc.bws.di

import com.devsinc.bws.retrofit.BookWithStarAPI
import com.devsinc.bws.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesBookWithStarAPI(retrofit: Retrofit): BookWithStarAPI {
        return retrofit.create(BookWithStarAPI::class.java)
    }
}