package com.devsinc.bws.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.devsinc.bws.api.AuthAPI
import com.devsinc.bws.api.BookWithStarAPI
import com.devsinc.bws.api.NetworkInterceptor
import com.devsinc.bws.model.NoConnectivityException
import com.devsinc.bws.model.NoInternetException
import com.devsinc.bws.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

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
    fun provideOkHttpClient(
        networkInterceptor: NetworkInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(NoConnectionInterceptor(context))
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

    class NoConnectionInterceptor(
        private val context: Context
    ) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            return if (!isConnectionOn()) {
                throw NoConnectivityException()
            } else if (!isInternetAvailable()) {
                throw NoInternetException()
            } else {
                chain.proceed(chain.request())
            }
        }

        private fun isInternetAvailable(): Boolean {
            return try {
                val timeoutMs = 1500
                val sock = Socket()
                val sockaddr = InetSocketAddress("8.8.8.8", 53)

                sock.connect(sockaddr, timeoutMs)
                sock.close()

                true
            } catch (e: IOException) {
                false
            }
        }

        private fun isConnectionOn(): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                        ConnectivityManager

            return postAndroidMInternetCheck(connectivityManager)
        }

        private fun postAndroidMInternetCheck(
            connectivityManager: ConnectivityManager
        ): Boolean {
            val network = connectivityManager.activeNetwork
            val connection =
                connectivityManager.getNetworkCapabilities(network)

            return connection != null && (
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        }

        private fun preAndroidMInternetCheck(
            connectivityManager: ConnectivityManager
        ): Boolean {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
            }
            return false
        }
    }
}
