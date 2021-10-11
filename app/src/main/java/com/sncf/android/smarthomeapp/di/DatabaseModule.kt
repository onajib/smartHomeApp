package com.sncf.android.smarthomeapp.di

import android.app.Application
import androidx.room.Room
import com.sncf.android.smarthomeapp.data.local.DataDao
import com.sncf.android.smarthomeapp.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: LocalDatabase.Callback): LocalDatabase =
        Room.databaseBuilder(application, LocalDatabase::class.java, "local_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun provideDataDao(db: LocalDatabase): DataDao = db.getDataDao()
}