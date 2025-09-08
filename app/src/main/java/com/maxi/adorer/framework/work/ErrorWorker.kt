package com.maxi.adorer.framework.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maxi.adorer.data.common.Constants.SMS_SENDING_ERROR
import com.maxi.adorer.domain.usecase.ErrorUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Instant

@HiltWorker
class ErrorWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val useCase: ErrorUseCase
) : CoroutineWorker(context, params) {

    companion object {
        const val ERROR_WORKER_TAG = "ErrorWorker"
    }

    override suspend fun doWork(): Result {
        val errorMessage = inputData.getString(SMS_SENDING_ERROR)
        return errorMessage?.let {
            try {
                useCase.invoke(it, Instant.now().toString())
                Result.success()
            } catch (e: Exception) {
                Log.e(ERROR_WORKER_TAG, "Error while inserting error in table: ${e.message}")
                Result.failure()
            }
        } ?: Result.failure()
    }
}