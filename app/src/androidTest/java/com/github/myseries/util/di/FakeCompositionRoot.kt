package com.github.myseries.util.di

import com.github.myseries.data.remote.TVMazeService
import com.github.myseries.di.CompositionRoot
import com.github.myseries.domain.RealSeriesRepository
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.ui.search.SearchViewModelFactory
import com.github.myseries.ui.series.SeriesViewModelFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FakeCompositionRoot : CompositionRoot {
    private val moshi by lazy { Moshi.Builder().build() }

    private val service: TVMazeService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TVMazeService::class.java)
    }

    private val seriesRepository: SeriesRepository by lazy { RealSeriesRepository(service) }

    override val seriesViewModelFactory by lazy { SeriesViewModelFactory(seriesRepository) }

    override val searchViewModelFactory by lazy { SearchViewModelFactory(seriesRepository) }

    companion object {
        var BASE_URL = ""
    }
}