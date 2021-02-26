package com.github.myseries.ui.search

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.myseries.R
import com.github.myseries.domain.model.NetworkException
import com.github.myseries.domain.model.Series
import com.github.myseries.fake.FakeSeriesRepository
import org.hamcrest.core.IsNot.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {
    @Test
    fun shouldDoSearchAndShowResult() {
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)

        onView(withId(R.id.query)).perform(typeText("girl"))
        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.list_search_result)).check(matches(ViewMatchers.isDisplayed()))
        onView(withText("Chicken girls")).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowEmptyResultMessage() {
        FakeSeriesRepository.result = listOf<Series>()
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)

        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.text_no_series_found)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.list_search_result)).check(matches(not(ViewMatchers.isDisplayed())))

    }

    @Test
    fun shouldShowConnectionErrorMessage() {
        FakeSeriesRepository.result = NetworkException.Connection
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)

        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.text_error)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.text_error)).check(matches(withText(R.string.connection_error)))
    }

    @Test
    fun shouldShowServerErrorMessage() {
        FakeSeriesRepository.result = NetworkException.Server
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)

        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.text_error)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.text_error)).check(matches(withText(R.string.server_error)))
    }
}