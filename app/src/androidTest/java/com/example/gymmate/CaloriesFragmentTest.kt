package com.example.gymmate

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.gymmate.calories.CaloriesFragment
import com.example.gymmate.homepage.HomeFragment
import com.example.gymmate.registration.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CaloriesFragmentTest {


    @Test
    fun testClickHome() {
        // SETUP

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.navigation_home)).perform(click())

    }

    @Test
    fun testClickCalory() {
        // SETUP

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.navigation_cals)).perform(click())

    }
}