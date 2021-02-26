package com.github.myseries

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.myseries.fake.TestApplication

class TestJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(loader: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(loader, TestApplication::class.java.name, context)
    }
}
