package com.sncf.android.smarthomeapp.di.network

import com.sncf.android.smarthomeapp.data.api.IModuloTechApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuloTechApiProvider @Inject constructor() {
    @Provides
    @Singleton
    fun provideModuloTechApi(retrofit: Retrofit): IModuloTechApi =
        retrofit.create(IModuloTechApi::class.java)
}