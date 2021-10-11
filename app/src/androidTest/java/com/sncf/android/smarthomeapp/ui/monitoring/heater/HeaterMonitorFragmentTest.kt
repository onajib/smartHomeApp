package com.sncf.android.smarthomeapp.ui.monitoring.heater

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.isChecked
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
class HeaterMonitorFragmentTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_editHeater_mode() {

        onView(withId(R.id.device_table_header_layout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_devices))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Radiateur - Chambre")), click()))

        onView(withId(R.id.tv_heater_name))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_heater_name)).check(matches(withText("Device : Radiateur - Chambre")))

        onView(withId(R.id.sm_heater_mode)).check(matches(isNotChecked()))

        onView(withId(R.id.sm_heater_mode)).perform(click())

        onView(withId(R.id.b_confirm_heater)).perform(click())

        onView(withId(R.id.device_table_header_layout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_devices))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Radiateur - Chambre")), click()))

        onView(withId(R.id.sm_heater_mode)).check(matches(isChecked()))
    }

    @Test
    fun test_editHeater_temperature() {

        onView(withId(R.id.device_table_header_layout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_devices))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Radiateur - Chambre")), click()))

        onView(withId(R.id.tv_heater_name))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_heater_name)).check(matches(withText("Device : Radiateur - Chambre")))

        onView(withId(R.id.s_heater_temperature)).check(matches(withValue(20F)))

        onView(withId(R.id.s_heater_temperature)).perform(setValue(22F))

        onView(withId(R.id.b_confirm_heater)).perform(click())

        onView(withId(R.id.rv_devices))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Radiateur - Chambre")), click()))

        onView(withId(R.id.s_heater_temperature)).check(matches(withValue(22F)))
    }
}