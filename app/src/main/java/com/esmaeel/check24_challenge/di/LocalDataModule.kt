package com.esmaeel.check24_challenge.di

import android.content.Context
import android.content.SharedPreferences
import com.esmaeel.check24_challenge.BuildConfig
import com.esmaeel.check24_challenge.data.remote.ProductsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(BuildConfig.prefs_name, Context.MODE_PRIVATE)

}
