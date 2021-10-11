package com.sncf.android.smarthomeapp.data.mapper

import com.sncf.android.smarthomeapp.data.model.DataResponseApi
import com.sncf.android.smarthomeapp.domain.mapper.IMapper
import com.sncf.android.smarthomeapp.ui.model.Data
import com.sncf.android.smarthomeapp.utils.Constants.HEATER_LABEL
import com.sncf.android.smarthomeapp.utils.Constants.LIGHT_LABEL
import com.sncf.android.smarthomeapp.utils.Constants.ROLLER_LABEL

object DataMapper : IMapper<DataResponseApi, Data> {

    override fun toUi(apiObject: DataResponseApi) = Data(
        lightDevices = apiObject.devices
            .filter { it.productType == LIGHT_LABEL }
            .map { LightMapper.toUi(it) },
        heaterDevices = apiObject.devices
            .filter { it.productType == HEATER_LABEL }
            .map { HeaterMapper.toUi(it) },
        rollerDevices = apiObject.devices
            .filter { it.productType == ROLLER_LABEL }
            .map { RollerMapper.toUi(it) },
        user = UserMapper.toUi(apiObject.user)
    )
}