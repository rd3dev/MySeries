package com.github.myseries.di

import com.github.myseries.ui.series.SeriesViewModelFactory
import com.github.myseries.ui.series.search.SearchViewModelFactory

class SeriesContainer : AppContainer() {
    val seriesViewModelFactory: SeriesViewModelFactory by lazy {
        SeriesViewModelFactory(showRepository)
    }

    val searchViewModelFactory: SearchViewModelFactory by lazy {
        SearchViewModelFactory(showRepository)
    }
}
