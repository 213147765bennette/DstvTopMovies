package com.dstv.movie.presentation.di.core

import com.dstv.movie.data.api.API
import com.dstv.movie.data.api.APIService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule() {


    /*internal means that the declarations are visible inside a module.
    A module in kotlin is a set of Kotlin files compiled together.*/
    //In Dagger 2, classes annotated with @Module are responsible for providing objects which can be injected
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(API.NETWORKING_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(API.NETWORKING_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder():Retrofit{
        return Retrofit.Builder().baseUrl(API.BASE_URL)
            .client(provideOkHttpClientBuilder())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(provideGsonBuilder()))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }


}