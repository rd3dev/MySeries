package com.github.myseries

import android.app.Application
import com.github.myseries.di.AppContainer
import com.github.myseries.di.SeriesContainer

class MySeriesApplication : Application() {

    val seriesContainer = SeriesContainer()

}