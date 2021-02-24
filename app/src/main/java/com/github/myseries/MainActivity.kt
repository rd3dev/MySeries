package com.github.myseries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.myseries.ui.series.SeriesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SeriesFragment.newInstance())
                    .commitNow()
        }
    }
}