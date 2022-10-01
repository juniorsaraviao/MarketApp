package com.mitocode.marketapp.di

import com.mitocode.marketapp.data.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRemoteService(): Api.ApiInterface{
        return Retrofit.Builder()
            .baseUrl("https://marketapp2021.herokuapp.com/")
            .build()
            .create()
    }
}