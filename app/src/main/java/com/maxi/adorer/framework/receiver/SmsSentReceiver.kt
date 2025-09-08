package com.maxi.adorer.framework.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.maxi.adorer.data.common.Constants.SMS_SENDING_ERROR
import com.maxi.adorer.framework.di.entrypoint.SmsReceiverEntryPoint
import com.maxi.adorer.framework.work.ErrorWorker
import com.maxi.adorer.framework.work.InsertQuoteWorker
import dagger.hilt.android.EntryPointAccessors

class SmsSentReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            SmsReceiverEntryPoint::class.java
        )

        val workManager = entryPoint.workManager()

        when (resultCode) {
            Activity.RESULT_OK -> {
                Log.d(SmsTAG, "$resultCode Message sent successfully")
                updateInsertQuoteWorkRequest(workManager)
            }

            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                val errorMessage = "$resultCode Generic failure"
                Log.e(SmsTAG, errorMessage)
                updateErrorWorkRequest(errorMessage, workManager)
            }

            SmsManager.RESULT_ERROR_NO_SERVICE -> {
                val errorMessage = "$resultCode No service"
                Log.e(SmsTAG, errorMessage)
                updateErrorWorkRequest(errorMessage, workManager)
            }

            SmsManager.RESULT_ERROR_NULL_PDU -> {
                val errorMessage = "$resultCode Null PDU"
                Log.e(SmsTAG, errorMessage)
                updateErrorWorkRequest(errorMessage, workManager)
            }

            SmsManager.RESULT_ERROR_RADIO_OFF -> {
                val errorMessage = "$resultCode Radio off"
                Log.e(SmsTAG, errorMessage)
                updateErrorWorkRequest(errorMessage, workManager)
            }
        }
    }

    private fun updateInsertQuoteWorkRequest(workManager: WorkManager) {
        val workRequest = OneTimeWorkRequestBuilder<InsertQuoteWorker>()
            .build()
        workManager.enqueue(workRequest)
    }

    private fun updateErrorWorkRequest(errorMessage: String, workManager: WorkManager) {
        val inputData = workDataOf(
            SMS_SENDING_ERROR to errorMessage
        )
        val workRequest = OneTimeWorkRequestBuilder<ErrorWorker>()
            .setInputData(inputData)
            .build()

        workManager.enqueue(workRequest)
    }
}

const val SmsTAG = "SmsReceiver"