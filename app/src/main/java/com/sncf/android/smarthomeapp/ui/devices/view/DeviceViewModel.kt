package com.sncf.android.smarthomeapp.ui.devices.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val deletedDeviceEventChannel = Channel<DeletedDeviceEvent>()

    val deletedDeviceEvent = deletedDeviceEventChannel.receiveAsFlow()

    fun getDevices() = dataRepository.getDevices()

    fun getUser() = dataRepository.getUser()

    fun onDeviceSwiped(device: Device) {
        viewModelScope.launch {
            dataRepository.deleteDevice(device)
            deletedDeviceEventChannel.send(DeletedDeviceEvent.ShowUndoDeleteDeviceMessage(device))
        }
    }

    fun onUndoDeleteClick(device: Device) {
        viewModelScope.launch {
            dataRepository.insertDevice(device)
        }
    }

    sealed class DeletedDeviceEvent {
        data class ShowUndoDeleteDeviceMessage(val device: Device) : DeletedDeviceEvent()
    }
}