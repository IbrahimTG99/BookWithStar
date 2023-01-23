package com.devsinc.bws.api

import android.util.Log
import com.devsinc.bws.utils.NetworkConstants
import okhttp3.Interceptor
import javax.inject.Inject


class NetworkInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${NetworkConstants.TOKEN}")
        return chain.proceed(request.build())
    }
}