package com.example.gymmate
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.gymmate.registration.LoginActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    public val activity = ActivityTestRule(LoginActivity::class.java)


    @Test
    fun onViewClicked() {
        onView(withId(R.id.btn_sign_in)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).check(matches(withText("Sign in"))).perform(click())


    }

    @Test
    fun onCreateAccountClicked()
    {
        onView(withId(R.id.btn_new_account)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_new_account)).check(matches(withText("Create new account")))
            .check(matches(isDisplayed()))
    }
}