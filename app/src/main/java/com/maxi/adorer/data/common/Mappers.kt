package com.maxi.adorer.data.common

import com.maxi.adorer.data.source.db.entity.QuotesEntity
import com.maxi.adorer.domain.model.Quote
import com.maxi.adorer.domain.model.toQuoteEntity

fun List<Quote>.toQuotesEntityList(): List<QuotesEntity> = map { it.toQuoteEntity() }