package com.example.minimusiclesson.di

import android.content.Context
import com.example.minimusiclesson.core.network.NetworkUtils
import com.example.minimusiclesson.data.remote.ApiService
import com.example.minimusiclesson.data.remote.APIHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return APIHelper().getInstance()
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(
        @ApplicationContext context: Context,
    ): NetworkUtils {
        return NetworkUtils(context)
    }

}