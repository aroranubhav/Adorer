package com.maxi.adorer.framework.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SmsDeliveredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (resultCode) {
            Activity.RESULT_OK -> Log.d(SmsTAG, "$resultCode Message delivered to recipient")
            Activity.RESULT_CANCELED -> Log.e(SmsTAG, "$resultCode Message not delivered")
        }
    }
}