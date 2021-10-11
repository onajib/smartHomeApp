package com.sncf.android.smarthomeapp.ui.monitoring.roller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.repository.DataRepository
import com.sncf.android.smarthomeapp.ui.model.Roller
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RollerMonitorViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val updatedRollerEventChannel = Channel<UpdatedRollerEvent>()

    val updatedRollerEvent = updatedRollerEventChannel.receiveAsFlow()

    fun updateRollerConfiguration(roller: Roller) {
        viewModelScope.launch {
            dataRepository.updateDevice(
                Device(
                    roller.id,
                    roller.deviceName,
                    position = roller.position,
                    productType = roller.productType
                )
            )
            updatedRollerEventChannel.send(UpdatedRollerEvent.ShowUpdatedRollerMessage("${roller.deviceName} updated"))
        }
    }

    sealed class UpdatedRollerEvent {
        data class ShowUpdatedRollerMessage(val message: String) : UpdatedRollerEvent()
    }
}