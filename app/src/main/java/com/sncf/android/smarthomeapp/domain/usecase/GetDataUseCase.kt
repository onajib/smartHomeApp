package com.sncf.android.smarthomeapp.domain.usecase

import com.sncf.android.smarthomeapp.data.api.IModuloTechApi
import com.sncf.android.smarthomeapp.data.mapper.DataMapper
import com.sncf.android.smarthomeapp.ui.model.Data
import com.sncf.android.smarthomeapp.utils.ErrorUtils.handleError
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetDataUseCase @Inject constructor() {

    @Inject
    lateinit var moduloTechApi: IModuloTechApi

    fun execute(): Observable<Data> {
        return moduloTechApi.getDevices().flatMapObservable { response ->
            when {
                response.isSuccessful -> response.body()?.let { data ->
                    Observable.just(DataMapper.toUi(data))
                }
                else -> throw handleError(
                    this::class.java.name,
                    response.errorBody()?.string(),
                    response.code()
                )
            }
        }
    }
}