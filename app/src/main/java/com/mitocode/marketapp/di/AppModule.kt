package com.mitocode.marketapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.data.datasource.GenderRemoteDataSource
import com.mitocode.marketapp.data.datasource.GenderServerDataSource
import com.mitocode.marketapp.data.datasource.UserRemoteDataSource
import com.mitocode.marketapp.data.datasource.UserServerDataSource
import com.mitocode.marketapp.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            Constants.PREFERENCE_TOKEN,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
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