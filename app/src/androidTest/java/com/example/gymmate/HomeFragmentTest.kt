package com.example.gymmate

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.gymmate.homepage.HomeFragment
import com.example.gymmate.registration.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {


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
        onView(withId(R.id.listView)).check(matches(isDisplayed()))
    }
}