package com.maxi.adorer.domain.repository

interface SmsSender {

    suspend fun sendMessage(number: String, message: String): Result<Unit>
}