package com.maxi.adorer.framework.work

import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.maxi.adorer.common.Constants.USER_NUMBER
import java.util.concurrent.TimeUnit

object QuotesWorkScheduler {

    private const val QUOTES_SENDING_WORK = "quotes_sending_work"

    fun scheduleWork(workManager: WorkManager, phoneNumber: String) {
        val workData = workDataOf(
            USER_NUMBER to phoneNumber
        )

        val workRequest = PeriodicWorkRequestBuilder<QuotesWorker>(
            30,
            TimeUnit.MINUTES
        )
            .setInputData(workData)
            .setInitialDelay(
                5,
                TimeUnit.MINUTES
            ).setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            ).build()


        workManager.enqueueUniquePeriodicWork(
            QUOTES_SENDING_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}