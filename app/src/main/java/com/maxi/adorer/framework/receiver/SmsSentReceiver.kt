package com.maxi.adorer.framework.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log

class SmsSentReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> Log.d(SmsTAG, "$resultCode Message sent successfully")
            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Log.e(SmsTAG, "$resultCode Generic failure")
            SmsManager.RESULT_ERROR_NO_SERVICE -> Log.e(SmsTAG, "$resultCode No service")
            SmsManager.RESULT_ERROR_NULL_PDU -> Log.e(SmsTAG, "$resultCode Null PDU")
            SmsManager.RESULT_ERROR_RADIO_OFF -> Log.e(SmsTAG, "$resultCode Radio off")
        }
    }
}

const val SmsTAG = "SmsReceiver"