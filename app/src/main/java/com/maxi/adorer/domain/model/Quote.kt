package com.maxi.adorer.domain.model

import com.maxi.adorer.data.source.db.entity.QuotesEntity

data class Quote(
    val id: String,
    val quote: String
)

fun Quote.toQuoteEntity(): QuotesEntity =
    QuotesEntity(id, quote)
