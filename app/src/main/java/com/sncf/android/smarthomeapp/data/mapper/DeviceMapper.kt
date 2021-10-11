package com.sncf.android.smarthomeapp.data.mapper

import com.sncf.android.smarthomeapp.data.model.DeviceApi
import com.sncf.android.smarthomeapp.domain.mapper.IMapper
import com.sncf.android.smarthomeapp.ui.model.Device

object DeviceMapper : IMapper<DeviceApi, Device> {

    override fun toUi(apiObject: DeviceApi) = Device(
        id = apiObject.id,
        deviceName = apiObject.deviceName,
        productType = apiObject.productType
    )
}