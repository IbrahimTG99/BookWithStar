package com.devsinc.bws.di

import com.devsinc.bws.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}