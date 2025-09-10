package com.maxi.adorer.framework.work

import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.maxi.adorer.common.Constants.USER_NUMBER
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

object QuotesWorkScheduler {

    private const val QUOTES_SENDING_WORK = "quotes_sending_work"

    fun scheduleWork(workManager: WorkManager, phoneNumber: String) {
        val workData = workDataOf(
            USER_NUMBER to phoneNumber
        )

        val initialDelay = calculateInitialDelay()

        val workRequest = PeriodicWorkRequestBuilder<QuotesWorker>(
            24,
            TimeUnit.HOURS
        )
            .setInputData(workData)
            .setInitialDelay(
                initialDelay,
                TimeUnit.MILLISECONDS
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

    private fun calculateInitialDelay(): Long {
        val currZone = ZoneId.systemDefault()
        val currTime = Instant.now()

        val time12PM: Instant = LocalDateTime.now()
            .withHour(12)
            .withMinute(0)
            .withSecond(0)
            .atZone(currZone)
            .toInstant()

        val targetTime = if (currTime.isAfter(time12PM)) {
            time12PM.plus(Duration.ofDays(1))
        } else {
            time12PM
        }

        val duration = Duration.between(currTime, targetTime)
        return duration.toMillis()
    }
}