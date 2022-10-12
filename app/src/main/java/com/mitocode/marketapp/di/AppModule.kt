package com.mitocode.marketapp.di

import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.remote.GenderRemoteDataSource
import com.mitocode.marketapp.data.remote.GenderServerDataSource
import com.mitocode.marketapp.data.remote.UserRemoteDataSource
import com.mitocode.marketapp.data.remote.UserServerDataSource
import dagger.Binds
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

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindUserRemoteDataSource(userServerDataSource: UserServerDataSource): UserRemoteDataSource

    @Binds
    abstract fun bindGenderRemoteDataSource(genderServerDataSource: GenderServerDataSource): GenderRemoteDataSource
}