package com.maxi.adorer.domain.usecase

import com.maxi.adorer.domain.repository.SmsSender
import javax.inject.Inject

class SmsSenderUseCase @Inject constructor(
    private val smsSender: SmsSender
) {

    suspend operator fun invoke(
        number: String, message: String
    ): Result<Unit> =
        smsSender.sendMessage(number, message)
}