package com.devsinc.bws.di

import com.devsinc.bws.data.AppDb
import com.devsinc.bws.repository.AuthRepository
import com.devsinc.bws.repository.AuthRepositoryImpl
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.CustomerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideCustomerRepository(impl: CustomerRepositoryImpl): CustomerRepository = impl
}