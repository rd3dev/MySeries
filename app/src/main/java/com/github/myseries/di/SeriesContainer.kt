package com.github.myseries.di

import com.github.myseries.ui.series.SeriesViewModelFactory

class SeriesContainer : AppContainer() {
    val seriesViewModelFactory: SeriesViewModelFactory by lazy {
        SeriesViewModelFactory(showRepository)
    }
}
    