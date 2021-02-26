package com.github.myseries.fake.di

import com.github.myseries.di.CompositionRoot
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.fake.FakeSeriesRepository
import com.github.myseries.ui.search.SearchViewModelFactory
import com.github.myseries.ui.series.SeriesViewModelFactory

open class FakeCompositionRoot : CompositionRoot {

    private val seriesRepository: SeriesRepository by lazy { FakeSeriesRepository() }

    override val seriesViewModelFactory by lazy { SeriesViewModelFactory(seriesRepository) }

    override val searchViewModelFactory by lazy { SearchViewModelFactory(seriesRepository) }
}
    