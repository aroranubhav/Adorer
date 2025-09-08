package com.maxi.adorer.framework.di.entrypoint

import androidx.work.WorkManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SmsReceiverEntryPoint {

    fun workManager(): WorkManager
}