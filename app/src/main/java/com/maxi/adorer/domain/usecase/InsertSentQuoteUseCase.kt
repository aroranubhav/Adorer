package com.maxi.adorer.domain.usecase

import com.maxi.adorer.domain.model.Quote
import com.maxi.adorer.domain.repository.QuotesRepository
import javax.inject.Inject

class InsertSentQuoteUseCase @Inject constructor(
    private val repository: QuotesRepository
) {

    suspend operator fun invoke(quote: Quote, dateTime: String) =
        repository.insertSentQuote(quote, dateTime)
}