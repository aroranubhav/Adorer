package com.maxi.adorer.domain.repository

import com.maxi.adorer.common.Resource
import com.maxi.adorer.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun seedDb(fileName: String): Resource<Unit>

    fun getQuote(): Flow<Resource<Quote>>

    suspend fun insertSentQuote(quote: Quote, dateTime: String): Resource<Unit>

    suspend fun insetQuote(quote: Quote): Resource<Unit>

    fun getQuotes(): Flow<Resource<List<Quote>>>
}