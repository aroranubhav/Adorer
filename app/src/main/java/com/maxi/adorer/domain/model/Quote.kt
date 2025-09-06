package com.maxi.adorer.domain.model

import com.maxi.adorer.data.source.db.entity.QuotesEntity
import com.maxi.adorer.data.source.db.entity.SentQuotesEntity

data class Quote(
    val id: String,
    val quote: String
)

fun Quote.toQuoteEntity(): QuotesEntity =
    QuotesEntity(id, quote)

fun Quote.toSentQuoteEntity(
    dateTime: String
): SentQuotesEntity =
    SentQuotesEntity(id, quote, dateTime)
