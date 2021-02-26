package com.github.myseries.di

import com.github.myseries.data.remote.TVMazeService
import com.github.myseries.domain.RealSeriesRepository
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.ui.search.SearchViewModelFactory
import com.github.myseries.ui.series.SeriesViewModelFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class AppCompositionRoot : CompositionRoot {
    private val moshi by lazy { Moshi.Builder().build() }

    private val interceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client by lazy { OkHttpClient.Builder().addInterceptor(interceptor).build() }

    private val service: TVMazeService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TVMazeService::class.java)
    }

    private val seriesRepository: SeriesRepository by lazy { RealSeriesRepository(service) }

    override val seriesViewModelFactory by lazy { SeriesViewModelFactory(seriesRepository) }

    override val searchViewModelFactory by lazy { SearchViewModelFactory(seriesRepository) }
}
    