package com.github.myseries.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(loader: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(loader, TestApplication::class.java.name, context)
    }
}
