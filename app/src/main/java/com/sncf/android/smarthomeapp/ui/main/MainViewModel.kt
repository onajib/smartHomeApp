package com.sncf.android.smarthomeapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sncf.android.smarthomeapp.base.BaseViewModel
import com.sncf.android.smarthomeapp.data.persistence.device.DeviceRepo
import com.sncf.android.smarthomeapp.data.persistence.device.DeviceRepoImpl
import com.sncf.android.smarthomeapp.domain.usecase.GetDataUseCase
import com.sncf.android.smarthomeapp.ui.model.Data
import com.sncf.android.smarthomeapp.ui.model.Device
import com.sncf.android.smarthomeapp.ui.model.User
import com.sncf.android.smarthomeapp.utils.ErrorUtils.evaluateErrorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase,
) : BaseViewModel(), IMainContract.ViewModel {

    private val _devices = MutableLiveData<ArrayList<Device>>()

    private val _user = MutableLiveData<User>()

    private val _errorMessage = MutableLiveData<Int>()

    ///////////////////////////////////////////////////////////////////////////
    // OVERRIDE
    ///////////////////////////////////////////////////////////////////////////

    override val devices: LiveData<ArrayList<Device>>
        get() = _devices

    override val user: LiveData<User>
        get() = _user

    override val errorMessage: LiveData<Int>
        get() = _errorMessage

    override fun getData() {
        getDataUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data -> evaluateData(data) },
                { _errorMessage.postValue(evaluateErrorCode(it)) })
    }

    private fun evaluateData(data: Data) {
        val deviceList = mutableListOf<Device>()
        deviceList.addAll(data.lightDevices)
        deviceList.addAll(data.heaterDevices)
        deviceList.addAll(data.rollerDevices)
        _devices.postValue(deviceList as ArrayList<Device>?)
        _user.postValue(data.user)
    }
}