package com.github.myseries

import android.app.Application
import com.github.myseries.di.AppCompositionRoot
import com.github.myseries.di.ComposableRoot
import com.github.myseries.di.CompositionRoot

class MyApplication : Application(), ComposableRoot {
    override val appCompositionRoot: CompositionRoot by lazy { AppCompositionRoot() }
}