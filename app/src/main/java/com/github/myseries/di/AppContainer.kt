package com.github.myseries.di

import com.github.myseries.data.remote.TVMazeService
import com.github.myseries.domain.ShowRepository
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


open class AppContainer {
    private val moshi = Moshi.Builder().build()

    private val service = Retrofit.Builder()
        .baseUrl("https://api.tvmaze.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(TVMazeService::class.java)

    val showRepository = ShowRepository(service)
}
    