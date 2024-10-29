package com.example.barcateam.network

import com.example.barcateam.BuildConfig
import com.example.barcateam.network.api.FootballAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun json(): Json {
        return Json {
            ignoreUnknownKeys = true // Ignores unknown fields in JSON, if any
        }
    }

    @Provides
    fun provideAPIKeyInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request()
                .newBuilder()
                .header("x-apisports-key", BuildConfig.FOOTBALL_API_KEY)
            val actualRequest = request.build()
            it.proceed(actualRequest)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.FOOTBALL_HOST_URL)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideFootballAPI(retrofit: Retrofit): FootballAPI {
        return retrofit.create(FootballAPI::class.java)
    }
}