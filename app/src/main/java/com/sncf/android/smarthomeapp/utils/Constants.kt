package com.sncf.android.smarthomeapp.utils

object Constants {

    const val BASE_URL = "http://storage42.com"
    const val LIGHT_LABEL = "Light"
    const val ROLLER_LABEL = "RollerShutter"
    const val HEATER_LABEL = "Heater"
    const val USER_LABEL = "User"

    val DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}\$".toRegex()
    val INT_REGEX = "\\d+(\\.\\d+)?".toRegex()
}