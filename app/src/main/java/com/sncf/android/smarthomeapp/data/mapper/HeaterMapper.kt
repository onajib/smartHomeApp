package com.sncf.android.smarthomeapp.data.mapper

import com.sncf.android.smarthomeapp.data.model.DeviceApi
import com.sncf.android.smarthomeapp.domain.mapper.IMapper
import com.sncf.android.smarthomeapp.ui.model.Heater

object HeaterMapper : IMapper<DeviceApi, Heater> {

    override fun toUi(apiObject: DeviceApi) = Heater(
        id = apiObject.id,
        deviceName = apiObject.deviceName,
        productType = apiObject.productType,
        mode = apiObject.mode,
        temperature = apiObject.temperature
    )
}