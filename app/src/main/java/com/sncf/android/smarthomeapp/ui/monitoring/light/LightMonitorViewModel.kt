package com.sncf.android.smarthomeapp.ui.monitoring.light

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.repository.DataRepository
import com.sncf.android.smarthomeapp.ui.model.Light
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LightMonitorViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val updatedLightEventChannel = Channel<UpdatedLightEvent>()

    val updatedLightEvent = updatedLightEventChannel.receiveAsFlow()

    fun updateLightConfiguration(light: Light) {
        viewModelScope.launch {
            dataRepository.updateDevice(
                Device(
                    light.id,
                    light.deviceName,
                    light.intensity,
                    light.mode,
                    productType = light.productType
                )
            )
            updatedLightEventChannel.send(UpdatedLightEvent.ShowUpdatedLightMessage("${light.deviceName} updated"))
        }
    }

    sealed class UpdatedLightEvent {
        data class ShowUpdatedLightMessage(val message: String) : UpdatedLightEvent()
    }
}