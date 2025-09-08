package com.maxi.adorer.domain.usecase

import com.maxi.adorer.domain.repository.QuotesRepository
import javax.inject.Inject

class ErrorUseCase @Inject constructor(
    private val repository: QuotesRepository
) {

    suspend operator fun invoke(errorMessage: String, dateTime: String) =
        repository.insertQuoteSendingError(errorMessage, dateTime)
}