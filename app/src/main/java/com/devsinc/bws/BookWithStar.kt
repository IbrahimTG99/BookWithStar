package com.devsinc.bws

import android.app.Application
import com.devsinc.bws.di.ApplicationComponent
import com.devsinc.bws.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookWithStar : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder().build()
    }
}