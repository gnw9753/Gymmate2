package com.example.gymmate

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gymmate.homepage.HomeFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReviewFragmentTest {

    @Test
    fun testVisible() {
        // SETUP

        val factory = FragmentFactory()
        val bundle = Bundle()
        val scenario = launchFragmentInContainer<HomeFragment>(
            fragmentArgs = bundle,
            factory = factory
        )

        // VERIFY
        Espresso.onView(ViewMatchers.withId(R.id.listView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}