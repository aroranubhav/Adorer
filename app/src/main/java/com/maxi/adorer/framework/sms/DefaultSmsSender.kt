package com.maxi.adorer.framework.sms

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import com.maxi.adorer.domain.repository.SmsSender
import com.maxi.adorer.framework.receiver.SmsDeliveredReceiver
import com.maxi.adorer.framework.receiver.SmsSentReceiver

class DefaultSmsSender(
    private val context: Context
) : SmsSender {
    override suspend fun sendMessage(number: String, message: String): Result<Unit> {
        return try {
            Log.d(DefaultSmsSenderTAG, "Current message to be sent: $message")
            val smsManager = context.getSystemService(SmsManager::class.java)

            val sentIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(
                    context,
                    SmsSentReceiver::class.java
                ),
                PendingIntent.FLAG_IMMUTABLE
            )

            val deliveredIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(
                    context,
                    SmsDeliveredReceiver::class.java
                ),
                PendingIntent.FLAG_IMMUTABLE
            )

            smsManager.sendTextMessage(
                number,
                null,
                getMessage(message),
                sentIntent,
                deliveredIntent
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getMessage(quote: String): String =
        buildString {
            append("Good Morning Sunshine!\n")
            append("$quote\n")
            append("XOXO")
        }
}

const val DefaultSmsSenderTAG = "DefaultSmsSender"