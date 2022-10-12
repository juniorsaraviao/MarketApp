package com.mitocode.marketapp.di

import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.data.datasource.GenderRemoteDataSource
import com.mitocode.marketapp.data.datasource.GenderServerDataSource
import com.mitocode.marketapp.data.datasource.UserRemoteDataSource
import com.mitocode.marketapp.data.datasource.UserServerDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService {
        return Retrofit.Builder()
            .baseUrl("https://marketapp2021.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
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