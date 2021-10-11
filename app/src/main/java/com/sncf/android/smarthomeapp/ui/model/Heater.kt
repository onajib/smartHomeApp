package com.sncf.android.smarthomeapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Heater(
    var id: Int,
    var deviceName: String,
    var productType: String,
    var mode: String?,
    var temperature: Int?
) : Parcelable