package com.github.myseries.ui.series

import com.github.myseries.util.MockWebServerRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.myseries.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SeriesFragmentTest {
    @get:Rule
    val serverRule = MockWebServerRule()

    @Test
    fun shouldShowSeriesInList() {
        serverRule.arrangeResponse(200,"shows_200.json")

        launchFragmentInContainer<SeriesFragment>(themeResId = R.style.Theme_MySeries)

        onView(ViewMatchers.withId(R.id.list_shows))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("Kirby Buckets")).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowRetryWhenServerReturnError() {
        serverRule.arrangeResponse(501,"")

        launchFragmentInContainer<SeriesFragment>(themeResId = R.style.Theme_MySeries)

        onView(ViewMatchers.withId(R.id.retry_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun shouldShowRetryWhenServerReturnInvalidJSON() {
        serverRule.arrangeResponse(200,"invalid.json")

        launchFragmentInContainer<SeriesFragment>(themeResId = R.style.Theme_MySeries)

        onView(ViewMatchers.withId(R.id.retry_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun shouldLoadSeriesAfterSuccessfullyRetry() {
        serverRule.arrangeResponse(500)
        serverRule.arrangeResponse(200,"shows_200.json")

        launchFragmentInContainer<SeriesFragment>(themeResId = R.style.Theme_MySeries)

        onView(ViewMatchers.withId(R.id.retry_button))
            .perform(click())
        onView(ViewMatchers.withText("Kirby Buckets")).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }
}