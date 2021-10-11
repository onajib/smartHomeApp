package com.sncf.android.smarthomeapp.ui.devices.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
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
import com.sncf.android.smarthomeapp.common.recycleview.RecyclerViewItemCountAssertion
import com.sncf.android.smarthomeapp.common.recycleview.hasItem
import com.sncf.android.smarthomeapp.ui.main.MainActivity
import com.sncf.android.smarthomeapp.utils.Constants.HEATER_LABEL
import com.sncf.android.smarthomeapp.utils.Constants.LIGHT_LABEL
import com.sncf.android.smarthomeapp.utils.Constants.ROLLER_LABEL
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DevicesFragmentTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_lightDevices_onLightFilter() {
        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))

        onView(withId(R.id.b_light_filter)).perform(click())

        onView(withId(R.id.rv_devices)).check(RecyclerViewItemCountAssertion(4))

        onView(withId(R.id.rv_devices)).check(matches(not(hasItem(hasDescendant(withText(HEATER_LABEL))))))

        onView(withId(R.id.rv_devices)).check(matches(not(hasItem(hasDescendant(withText(ROLLER_LABEL))))))
    }

    @Test
    fun test_heaterDevices_onHeaterFilter() {
        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))

        onView(withId(R.id.b_heater_filter)).perform(click())

        onView(withId(R.id.rv_devices)).check(RecyclerViewItemCountAssertion(4))

        onView(withId(R.id.rv_devices)).check(matches(not(hasItem(hasDescendant(withText(LIGHT_LABEL))))))

        onView(withId(R.id.rv_devices)).check(matches(not(hasItem(hasDescendant(withText(ROLLER_LABEL))))))
    }

    @Test
    fun test_rollerDevices_onRollerFilter() {
        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))

        onView(withId(R.id.b_roller_filter)).perform(click())

        onView(withId(R.id.rv_devices)).check(RecyclerViewItemCountAssertion(4))

        onView(withId(R.id.rv_devices)).check(matches(not(hasItem(hasDescendant(withText(HEATER_LABEL))))))

        onView(withId(R.id.rv_devices)).check(matches(not(hasItem(hasDescendant(withText(LIGHT_LABEL))))))
    }

    @Test
    fun test_selectLightItem_isMonitoringFragmentVisible() {
        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Lampe - Cuisine")),
                    click()
                )
            )

        onView(withId(R.id.tv_light_name)).check(matches(withText("Device : Lampe - Cuisine")))
    }

    @Test
    fun test_lightBackNavigation_toDevicesFragment() {
        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Lampe - Cuisine")),
                    click()
                )
            )

        onView(withId(R.id.tv_light_name)).check(matches(withText("Device : Lampe - Cuisine")))

        pressBack()

        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_selectHeaterItem_isMonitoringFragmentVisible() {
        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Radiateur - Chambre")),
                    click()
                )
            )

        onView(withId(R.id.tv_heater_name)).check(matches(withText("Device : Radiateur - Chambre")))
    }

    @Test
    fun test_heaterBackNavigation_toDevicesFragment() {
        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Radiateur - Chambre")),
                    click()
                )
            )

        onView(withId(R.id.tv_heater_name)).check(matches(withText("Device : Radiateur - Chambre")))

        pressBack()

        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_selectRollerItem_isMonitoringFragmentVisible() {
        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Volet roulant - Salon")),
                    click()
                )
            )

        onView(withId(R.id.tv_roller_name)).check(matches(withText("Device : Volet roulant - Salon")))
    }

    @Test
    fun test_rollerBackNavigation_toDevicesFragment() {
        onView(withId(R.id.rv_devices))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Volet roulant - Salon")),
                    click()
                )
            )

        onView(withId(R.id.tv_roller_name)).check(matches(withText("Device : Volet roulant - Salon")))

        pressBack()

        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_profile_isProfileFragmentVisible() {
        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))
    }

    @Test
    fun test_profileBackNavigation_toDevicesFragment() {
        onView(withId(R.id.b_profile)).perform(click())
        onView(withId(R.id.tv_profile)).check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.rv_devices))
            .check(matches(isDisplayed()))
    }
}