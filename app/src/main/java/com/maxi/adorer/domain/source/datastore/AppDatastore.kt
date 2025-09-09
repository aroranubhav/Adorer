package com.maxi.adorer.domain.source.datastore

import com.maxi.adorer.domain.model.Quote

interface AppDatastore {

    suspend fun checkDbSeedingDone(): Boolean

    suspend fun markDbSeedingDone()

    suspend fun getCurrentQuote(): Quote?

    suspend fun saveCurrentQuote(quote: Quote?)
}