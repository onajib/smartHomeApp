package com.sncf.android.smarthomeapp.di.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

private const val HTTP_CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MiB
private const val HTTP_CACHE_CHILD = "http"
private const val CONNECT_TIMEOUT = 3000L
private const val WRITE_TIMEOUT = 3000L
private const val READ_TIMEOUT = 3000L

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientProvider @Inject constructor() {
    ///////////////////////////////////////////////////////////////////////////
    // IMPLEMENTS Provider
    ///////////////////////////////////////////////////////////////////////////

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext application: Context): OkHttpClient {
        val httpCache = Cache(
            File(
                application.cacheDir, HTTP_CACHE_CHILD
            ), HTTP_CACHE_SIZE
        )

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(httpCache)
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}