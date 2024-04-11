package com.example.my_dayle_stats

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().
        setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()


    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(ZonnedDateTimeAdapter())
        .build()



    private val retrofit = Retrofit.Builder()
        .baseUrl("https://statistics-api.wildberries.ru")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    val wbApi : WbApi
    get() = retrofit.create()
}