package com.maxi.adorer.domain.usecase

import com.maxi.adorer.common.Resource
import com.maxi.adorer.domain.model.Quote
import com.maxi.adorer.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuoteUseCase @Inject constructor(
    private val repository: QuotesRepository
) {

    operator fun invoke(): Flow<Resource<Quote>> =
        repository.getQuote()
}