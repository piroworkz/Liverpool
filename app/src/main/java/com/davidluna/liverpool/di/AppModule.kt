package com.davidluna.liverpool.di

import com.davidluna.liverpool.framework.services.ProductSearchService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBaseUrl() = "https://shoppapp.liverpool.com.mx/"

    @Singleton
    @Provides
    fun provideClient(
    ): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(this)
            .build()
    }

    @Singleton
    @Provides
    fun provideJsonConverter(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient,
        json: Json,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    fun provideProductSearchService(retrofit: Retrofit): ProductSearchService =
        retrofit.create(ProductSearchService::class.java)

}