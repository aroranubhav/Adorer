package com.maxi.adorer.framework.sms

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import com.maxi.adorer.common.Constants.SMS_DELIVERED_ACTION
import com.maxi.adorer.common.Constants.SMS_SENT_ACTION
import com.maxi.adorer.domain.repository.SmsSender

class DefaultSmsSender(
    private val context: Context
) : SmsSender {
    override suspend fun sendMessage(number: String, message: String): Result<Unit> {
        return try {
            Log.d(DefaultSmsSenderTAG, "Current message to be sent: $message")
            val smsManager = SmsManager.getDefault()

            val sentIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(SMS_SENT_ACTION),
                PendingIntent.FLAG_IMMUTABLE
            )

            val deliveredIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(SMS_DELIVERED_ACTION),
                PendingIntent.FLAG_IMMUTABLE
            )

            smsManager.sendTextMessage(number, null, message, sentIntent, deliveredIntent)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

const val DefaultSmsSenderTAG = "DefaultSmsSender"