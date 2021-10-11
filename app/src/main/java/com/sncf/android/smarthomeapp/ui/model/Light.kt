package com.sncf.android.smarthomeapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Light(
    var id: Int,
    var deviceName: String,
    var productType: String,
    var intensity: Int?,
    var mode: String?
) : Parcelable