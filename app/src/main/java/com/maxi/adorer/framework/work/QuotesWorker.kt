package com.maxi.adorer.framework.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maxi.adorer.common.Constants.USER_NUMBER
import com.maxi.adorer.common.Resource
import com.maxi.adorer.domain.usecase.GetQuoteUseCase
import com.maxi.adorer.domain.usecase.SmsSenderUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class QuotesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val getQuoteUseCase: GetQuoteUseCase,
    private val sendSmsUseCase: SmsSenderUseCase
) : CoroutineWorker(context, params) {

    companion object {
        const val QUOTES_WORKER_TAG = "QuotesWorker"
    }

    override suspend fun doWork(): Result {
        val number = inputData.getString(USER_NUMBER).orEmpty()

        return when (val result = getQuoteUseCase.invoke().first()) {
            is Resource.Success -> {
                val quote = result.data.quote

                val response = sendSmsUseCase.invoke(
                    number,
                    quote
                )

                if (response.isSuccess) {
                    Log.d(QUOTES_WORKER_TAG, "Message sent initiated successfully!")
                    Result.success()
                } else {
                    Result.retry()
                }
            }

            is Resource.UnknownError -> {
                Log.e(QUOTES_WORKER_TAG, "Work request failed permanently : $result")
                Result.failure()
            }

            else -> {
                Log.d(QUOTES_WORKER_TAG, "Work request failed, will retry : $result")
                Result.retry()
            }
        }
    }
}