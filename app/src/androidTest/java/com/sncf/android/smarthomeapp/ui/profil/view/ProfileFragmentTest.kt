package com.sncf.android.smarthomeapp.ui.profil.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {

    private val errorMessage = "Please fill in the field or check it's format"

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_profile_errorEmptyLastName() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_last_name)).perform(
            clearText()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.e_last_name)).perform(
           click()
        )

        onView(withId(R.id.e_last_name)).check(matches(hasErrorText(errorMessage)))
    }

    @Test
    fun test_profile_errorEmptyFirstName() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_first_name)).perform(
            clearText()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.e_first_name)).perform(
            click()
        )

        onView(withId(R.id.e_first_name)).check(matches(hasErrorText(errorMessage)))
    }

    @Test
    fun test_profile_errorEmptyStreet() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_street)).perform(
            clearText()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.e_street)).perform(
            click()
        )

        onView(withId(R.id.e_street)).check(matches(hasErrorText(errorMessage)))
    }

    @Test
    fun test_profile_errorEmptyStreetCode() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_street_code)).perform(
            clearText()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.e_street_code)).perform(
            click()
        )

        onView(withId(R.id.e_street_code)).check(matches(hasErrorText(errorMessage)))
    }

    @Test
    fun test_profile_errorEmptyCity() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_city)).perform(
            clearText()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.e_city)).perform(
            click()
        )

        onView(withId(R.id.e_city)).check(matches(hasErrorText(errorMessage)))
    }

    @Test
    fun test_profile_errorEmptyCountry() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_country)).perform(
            clearText()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.e_country)).perform(
            click()
        )
        onView(withId(R.id.e_country)).check(matches(hasErrorText(errorMessage)))
    }

    @Test
    fun test_profile_errorPostalCode() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_postal_code)).perform(
            clearText(),
            typeText("AAAAA"), closeSoftKeyboard()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.e_postal_code)).perform(click())

        onView(withId(R.id.e_postal_code)).check(matches(hasErrorText(errorMessage)))
    }

    @Test
    fun test_profile_edition() {

        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))

        onView(withId(R.id.e_last_name)).perform(
            clearText(),
            typeText("Dupont"), closeSoftKeyboard()
        )

        onView(withId(R.id.e_first_name)).perform(
            clearText(),
            typeText("Jean"), closeSoftKeyboard()
        )

        onView(withId(R.id.e_birth_date))
            .perform(click())
        onView(withText("OK")).perform(click())

        onView(withId(R.id.e_street)).perform(
            clearText(),
            typeText("Street"), closeSoftKeyboard()
        )

        onView(withId(R.id.e_street_code)).perform(
            clearText(),
            typeText("10"), closeSoftKeyboard()
        )

        onView(withId(R.id.e_city)).perform(
            clearText(),
            typeText("Paris"), closeSoftKeyboard()
        )

        onView(withId(R.id.e_country)).perform(
            clearText(),
            typeText("France"), closeSoftKeyboard()
        )

        onView(withId(R.id.b_confirm_user)).perform(click())

        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))

        onView(withId(R.id.b_profile)).perform(click())

        onView(withId(R.id.e_last_name))
            .check(matches(withText("Dupont")))

        onView(withId(R.id.e_first_name))
            .check(matches(withText("Jean")))

        onView(withId(R.id.e_city))
            .check(matches(withText("Paris")))

        onView(withId(R.id.e_country))
            .check(matches(withText("France")))
    }
}