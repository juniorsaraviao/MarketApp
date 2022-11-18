package com.mitocode.marketapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mitocode.marketapp.application.App
import com.mitocode.marketapp.data.datasource.*
import com.mitocode.marketapp.data.local.AppDatabase
import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String {
        return "https://marketapp2021.herokuapp.com/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }


    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "marketDB"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun providePurchasedProductDao(db: AppDatabase) = db.purchasedProductDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindUserRemoteDataSource(userServerDataSource: UserServerDataSource): UserRemoteDataSource

    @Binds
    abstract fun bindGenderRemoteDataSource(genderServerDataSource: GenderServerDataSource): GenderRemoteDataSource

    @Binds
    abstract fun bindCategoryRemoteDataSource(categoryServerDataSource: CategoryServerDataSource): CategoryRemoteDataSource

    @Binds
    abstract fun bindProductRemoteDataSource(productServerDataSource: ProductServerDataSource): ProductRemoteDataSource

    @Binds
    abstract fun bindCategoryLocalDataSource(categoryLocalDataSourceImp: CategoryLocalDataSourceImp): CategoryLocalDataSource

    @Binds
    abstract fun bindPurchasedProductLocalDataSource(purchasedProductLocalDataSourceImp: PurchasedProductLocalDataSourceImp): PurchasedProductLocalDataSource
}