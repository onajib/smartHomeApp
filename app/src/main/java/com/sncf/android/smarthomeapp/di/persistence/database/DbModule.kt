package com.sncf.android.smarthomeapp.di.persistence.database

import android.content.Context
import androidx.room.Room
import com.sncf.android.smarthomeapp.di.persistence.database.device.DeviceDao
import com.sncf.android.smarthomeapp.di.persistence.database.device.DeviceDb
import com.sncf.android.smarthomeapp.utils.Constants.DB_NAME
import com.sncf.android.smarthomeapp.utils.Constants.DB_NAME_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    @Named(DB_NAME_KEY)
    internal fun provideDeviceDb(context: Context): DeviceDb {
        return Room.databaseBuilder(context, DeviceDb::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideDeviceDao(context: Context): DeviceDao {
        return provideDeviceDb(context).deviceDao()
    }
}