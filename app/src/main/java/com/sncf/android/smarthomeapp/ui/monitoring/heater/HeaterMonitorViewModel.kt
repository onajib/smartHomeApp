package com.sncf.android.smarthomeapp.ui.monitoring.heater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.repository.DataRepository
import com.sncf.android.smarthomeapp.ui.model.Heater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeaterMonitorViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val updatedHeaterEventChannel = Channel<UpdatedHeaterEvent>()

    val updatedHeaterEvent = updatedHeaterEventChannel.receiveAsFlow()

    fun updateHeaterConfiguration(heater: Heater) {
        viewModelScope.launch {
            dataRepository.updateDevice(
                Device(
                    heater.id,
                    heater.deviceName,
                    mode = heater.mode,
                    temperature = heater.temperature,
                    productType = heater.productType
                )
            )
            updatedHeaterEventChannel.send(UpdatedHeaterEvent.ShowUpdatedHeaterMessage("${heater.deviceName} updated"))
        }
    }

    sealed class UpdatedHeaterEvent {
        data class ShowUpdatedHeaterMessage(val message: String) : UpdatedHeaterEvent()
    }
}