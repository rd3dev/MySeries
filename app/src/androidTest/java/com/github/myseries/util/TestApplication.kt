package com.github.myseries.util

import android.app.Application
import com.github.myseries.di.ComposableRoot
import com.github.myseries.di.CompositionRoot
import com.github.myseries.util.di.FakeCompositionRoot

class TestApplication : Application(), ComposableRoot {
    override val appCompositionRoot: CompositionRoot
        get() = FakeCompositionRoot()
}
