package com.github.myseries.di

import com.github.myseries.ui.search.SearchViewModelFactory
import com.github.myseries.ui.series.SeriesViewModelFactory

interface CompositionRoot {
    val seriesViewModelFactory: SeriesViewModelFactory
    val searchViewModelFactory: SearchViewModelFactory
}
