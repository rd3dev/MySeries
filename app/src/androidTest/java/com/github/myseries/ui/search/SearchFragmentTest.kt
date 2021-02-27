package com.github.myseries.ui.search

import com.github.myseries.util.MockWebServerRule
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
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {
    @get:Rule
    val serverRule = MockWebServerRule()

    @Test
    fun shouldDoSearchAndShowResult() {
        serverRule.arrangeResponse(200, "search_200.json", "/search/shows?q=girls")

        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)
        onView(withId(R.id.query)).perform(typeText("girls"))
        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.list_search_result)).check(matches(ViewMatchers.isDisplayed()))
        onView(withText("Girls")).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun shouldShowEmptyResultMessage() {
        serverRule.arrangeResponse(200, "search_empty_200.json")

        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)
        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.text_no_series_found)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.list_search_result)).check(matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun shouldShowConnectionErrorMessage() {
        serverRule.arrangeResponse(200, "search_empty_200.json")

        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)
        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.text_error)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.text_error)).check(matches(withText(R.string.connection_error)))
    }

    @Test
    fun shouldShowServerErrorMessage() {
        serverRule.arrangeResponse(500)

        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)
        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.text_error)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.text_error)).check(matches(withText(R.string.server_error)))
    }
}