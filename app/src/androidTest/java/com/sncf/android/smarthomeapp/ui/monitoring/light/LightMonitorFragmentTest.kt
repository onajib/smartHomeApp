package com.sncf.android.smarthomeapp.ui.monitoring.light

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LightMonitorFragmentTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_editLight_mode() {
        onView(withId(R.id.device_table_header_layout))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Lampe - Cuisine")),
                    ViewActions.click()
                )
            )

        onView(withId(R.id.tv_light_name))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.tv_light_name))
            .check(matches(ViewMatchers.withText("Device : Lampe - Cuisine")))

        onView(withId(R.id.sm_light_mode))
            .check(matches(ViewMatchers.isChecked()))

        onView(withId(R.id.sm_light_mode)).perform(ViewActions.click())

        onView(withId(R.id.b_confirm_light)).perform(ViewActions.click())

        onView(withId(R.id.device_table_header_layout))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Lampe - Cuisine")),
                    ViewActions.click()
                )
            )

        onView(withId(R.id.sm_light_mode))
            .check(matches(ViewMatchers.isNotChecked()))
    }
}