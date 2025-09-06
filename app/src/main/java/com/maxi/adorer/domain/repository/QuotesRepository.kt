package com.maxi.adorer.domain.repository

import com.maxi.adorer.common.Resource
import com.maxi.adorer.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun seedDb(): Resource<Unit>

    fun getQuotes(): Resource<Flow<Quote>>

    suspend fun insertQuote(quote: Quote): Resource<Unit>
}