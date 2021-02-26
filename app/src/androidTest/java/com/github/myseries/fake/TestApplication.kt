package com.github.myseries.fake

import android.app.Application
import com.github.myseries.di.ComposableRoot
import com.github.myseries.di.CompositionRoot
import com.github.myseries.fake.di.FakeCompositionRoot

class TestApplication : Application(), ComposableRoot {
    override val appCompositionRoot: CompositionRoot
        get() = FakeCompositionRoot()
}
