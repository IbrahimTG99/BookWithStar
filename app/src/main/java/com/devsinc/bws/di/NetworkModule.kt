package com.devsinc.bws.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.devsinc.bws.api.AuthAPI
import com.devsinc.bws.api.BookWithStarAPI
import com.devsinc.bws.api.NetworkInterceptor
import com.devsinc.bws.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    fun provideOkHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkInterceptor)
            .build()
    }

    @Provides
    fun provideBookWithStarAPI(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): BookWithStarAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(BookWithStarAPI::class.java)
    }

    @Provides
    fun provideAuthAPI(retrofitBuilder: Retrofit.Builder): AuthAPI {
        return retrofitBuilder
            .build()
            .create(AuthAPI::class.java)
    }
}