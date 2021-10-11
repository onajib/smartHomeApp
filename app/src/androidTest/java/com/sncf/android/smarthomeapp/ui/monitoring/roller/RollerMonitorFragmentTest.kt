package com.sncf.android.smarthomeapp.ui.monitoring.roller

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.common.values.Values.setValue
import com.sncf.android.smarthomeapp.common.values.Values.withValue
import com.sncf.android.smarthomeapp.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RollerMonitorFragmentTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_editroller_position() {

        onView(withId(R.id.device_table_header_layout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Volet roulant - Salon")), click()
                )
            )

        onView(withId(R.id.tv_roller_name))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_roller_name)).check(matches(withText("Device : Volet roulant - Salon")))

        onView(withId(R.id.s_roller_position)).check(matches(withValue(70F)))

        onView(withId(R.id.s_roller_position)).perform(setValue(60F))

        onView(withId(R.id.b_confirm_roller)).perform(click())

        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Volet roulant - Salon")), click()
                )
            )

        onView(withId(R.id.s_roller_position)).check(matches(withValue(60F)))
    }
}