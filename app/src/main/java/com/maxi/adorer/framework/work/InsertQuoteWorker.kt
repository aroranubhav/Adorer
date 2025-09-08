package com.maxi.adorer.framework.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maxi.adorer.domain.source.datastore.AppDatastore
import com.maxi.adorer.domain.usecase.InsertQuoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Instant

@HiltWorker
class InsertQuoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val useCase: InsertQuoteUseCase,
    private val datastore: AppDatastore
) : CoroutineWorker(context, params) {

    companion object {
        const val INSERT_QUOTE_WORKER_TAG = "InsertQuoteWorker"
    }

    override suspend fun doWork(): Result {
        val quote = datastore.getCurrentQuote()
        return quote?.let {
            try {
                useCase.invoke(
                    it,
                    Instant.now().toString()
                )
                Result.success()
            } catch (e: Exception) {
                Log.e(INSERT_QUOTE_WORKER_TAG, "Error while inserting quote: ${e.message}")
                Result.failure()
            }
        } ?: Result.failure()
    }
}