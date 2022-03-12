package com.esmaeel.check24_challenge.di

import com.esmaeel.check24_challenge.BuildConfig
import com.esmaeel.check24_challenge.data.remote.ProductsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  copied
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideProductsService(retrofit: Retrofit.Builder): ProductsService = retrofit
        .baseUrl(BuildConfig.base_url)
        .build()
        .create(ProductsService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )


    @Provides
    @Singleton
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

}
