package com.sncf.android.smarthomeapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sncf.android.smarthomeapp.data.model.DataResponse
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.data.model.User
import com.sncf.android.smarthomeapp.repository.DataRepository
import com.sncf.android.smarthomeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val data: MutableLiveData<Resource<DataResponse>> = MutableLiveData()

    var dataResponse: DataResponse? = null

    fun checkDbData() = dataRepository.getUser()

    fun getData() = viewModelScope.launch { dataCall() }

    fun insertDevices(device: List<Device>) {
        viewModelScope.launch {
            dataRepository.insertDevices(device)
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            dataRepository.insertUser(user)
        }
    }

    private suspend fun dataCall() {
        data.postValue(Resource.Loading())
        val response = dataRepository.getData()
        data.postValue(handleDataResponse(response))
    }

    private fun handleDataResponse(response: Response<DataResponse>): Resource<DataResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                dataResponse
                return Resource.Success(dataResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}