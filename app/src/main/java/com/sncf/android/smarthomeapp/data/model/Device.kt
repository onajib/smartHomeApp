package com.sncf.android.smarthomeapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "device_table")
open class Device(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var deviceName: String,
    var intensity: Int? = null,
    var mode: String? = null,
    var position: Int? = null,
    var temperature: Int? = null,
    var productType: String
) : Parcelable {
    override fun toString() = productType
}