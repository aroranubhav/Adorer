package com.maxi.adorer.domain.repository

import com.maxi.adorer.common.Resource

interface SmsSender {

    suspend fun sendMessage(number: String, message: String): Resource<Unit>
}