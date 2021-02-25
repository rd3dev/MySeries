package com.github.myseries.di

import com.github.myseries.data.remote.TVMazeService
import com.github.myseries.domain.ShowRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


open class AppContainer {
    private val moshi = Moshi.Builder().build()

    private val interceptor =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client =  OkHttpClient.Builder().addInterceptor(interceptor).build();

    private val service = Retrofit.Builder()
        .baseUrl("https://api.tvmaze.com")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(TVMazeService::class.java)

    val showRepository = ShowRepository(service)
}
    