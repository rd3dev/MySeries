package com.github.myseries.ui.search

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.myseries.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {
    @Test
    fun shouldShowResultList() {
        launchFragmentInContainer<SearchFragment>(themeResId = R.style.Theme_MySeries)

        onView(withId(R.id.search)).perform(click())

        onView(withId(R.id.list_search_result)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Chicken girls")).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }
}