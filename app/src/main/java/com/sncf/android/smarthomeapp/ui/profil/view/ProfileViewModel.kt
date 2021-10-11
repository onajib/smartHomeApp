package com.sncf.android.smarthomeapp.ui.profil.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sncf.android.smarthomeapp.data.model.Address
import com.sncf.android.smarthomeapp.data.model.User
import com.sncf.android.smarthomeapp.repository.DataRepository
import com.sncf.android.smarthomeapp.ui.profil.form.ProfileForm
import com.sncf.android.smarthomeapp.utils.Constants.DATE_REGEX
import com.sncf.android.smarthomeapp.utils.DateUtils.isValidDate
import com.sncf.android.smarthomeapp.utils.DateUtils.parseToLong
import com.sncf.android.smarthomeapp.utils.TextUtils.isEmpty
import com.sncf.android.smarthomeapp.utils.TextUtils.isIntParsable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val updatedUserEventChannel = Channel<UpdatedUserEvent>()
    private val _isLastNameOnError = MutableLiveData<Boolean>()
    private val _isFirstNameOnError = MutableLiveData<Boolean>()
    private val _isBirthDateOnError = MutableLiveData<Boolean>()
    private val _isStreetOnError = MutableLiveData<Boolean>()
    private val _isStreetCodeOnError = MutableLiveData<Boolean>()
    private val _isPostalCodeOnError = MutableLiveData<Boolean>()
    private val _isCityOnError = MutableLiveData<Boolean>()
    private val _isCountryOnError = MutableLiveData<Boolean>()

    val updatedUserEvent = updatedUserEventChannel.receiveAsFlow()

    val isLastNameOnError: LiveData<Boolean>
        get() = _isLastNameOnError

    val isFirstNameOnError: LiveData<Boolean>
        get() = _isFirstNameOnError

    val isBirthDateOnError: LiveData<Boolean>
        get() = _isBirthDateOnError

    val isStreetOnError: LiveData<Boolean>
        get() = _isStreetOnError

    val isStreetCodeOnError: LiveData<Boolean>
        get() = _isStreetCodeOnError

    val isPostalCodeOnError: LiveData<Boolean>
        get() = _isPostalCodeOnError

    val isCityOnError: LiveData<Boolean>
        get() = _isCityOnError

    val isCountryOnError: LiveData<Boolean>
        get() = _isCountryOnError

    fun submitForm(form: ProfileForm) {
        if (!form.isValid()) {
            postFormError(form)
        } else {
            viewModelScope.launch {
                dataRepository.updateUser(
                    User(
                        id = form.id,
                        firstName = form.firstName!!,
                        lastName = form.lastName!!,
                        address = Address(
                            city = form.city!!,
                            postalCode = form.postalCode!!.toInt(),
                            street = form.street!!,
                            streetCode = form.streetCode!!,
                            country = form.country!!
                        ),
                        birthDate = parseToLong(form.birthDate!!)
                    )
                )
                updatedUserEventChannel.send(UpdatedUserEvent.ShowUpdatedUserMessage("User updated"))
            }
        }
    }

    private fun postFormError(form: ProfileForm) {
        _isLastNameOnError.postValue(isEmpty(form.lastName))
        _isFirstNameOnError.postValue(isEmpty(form.firstName))
        _isBirthDateOnError.postValue(!isValidDate(form.birthDate, DATE_REGEX))
        _isStreetOnError.postValue(isEmpty(form.street))
        _isStreetCodeOnError.postValue(isEmpty(form.streetCode))
        _isPostalCodeOnError.postValue(!isIntParsable(form.postalCode))
        _isCityOnError.postValue(isEmpty(form.city))
        _isCountryOnError.postValue(isEmpty(form.country))
    }

    sealed class UpdatedUserEvent {
        data class ShowUpdatedUserMessage(val message: String) : UpdatedUserEvent()
    }
}