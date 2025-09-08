package com.maxi.adorer.data.repository

import com.maxi.adorer.common.Resource
import com.maxi.adorer.data.common.safeDbCall
import com.maxi.adorer.data.common.toQuotesEntityList
import com.maxi.adorer.data.source.db.dao.ErrorsDao
import com.maxi.adorer.data.source.db.dao.QuotesDao
import com.maxi.adorer.data.source.db.dao.SentQuotesDao
import com.maxi.adorer.data.source.db.entity.ErrorsEntity
import com.maxi.adorer.data.source.db.entity.toQuote
import com.maxi.adorer.domain.model.Quote
import com.maxi.adorer.domain.model.toQuoteEntity
import com.maxi.adorer.domain.model.toSentQuoteEntity
import com.maxi.adorer.domain.repository.QuotesRepository
import com.maxi.adorer.domain.source.datastore.AppDatastore
import com.maxi.adorer.domain.source.filesystem.FileReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultQuotesRepository @Inject constructor(
    private val quotesDao: QuotesDao,
    private val sentQuotesDao: SentQuotesDao,
    private val errorsDao: ErrorsDao,
    private val fileReader: FileReader,
    private val dataStore: AppDatastore
) : QuotesRepository {

    override suspend fun seedDb(fileName: String): Resource<Unit> {
        return when (val insertResponse = safeDbCall {
            val quotes = fileReader.readQuotesFromFile(fileName)
            quotesDao.insertQuotes(quotes.toQuotesEntityList())
        }) {
            is Resource.Success -> {
                dataStore.markDbSeedingDone()
                Resource.Success(Unit)
            }

            is Resource.DatabaseError -> {
                Resource.DatabaseError(insertResponse.errorMessage)
            }

            is Resource.IOError -> {
                Resource.IOError(insertResponse.errorMessage)
            }

            is Resource.UnknownError -> {
                Resource.UnknownError
            }
        }
    }

    /**
     * The flow starts emitting QuoteEntity objects.
     * Each emitted QuoteEntity is mapped to Resource.Success(Quote).
     * Each resulting Resource.Success(Quote) is passed to the collect block one at a time.
     * Without .collect, the flow is defined but inactive — nothing happens until you collect.
     */
    override fun getQuote(): Flow<Resource<Quote>> = flow {
        safeDbCall {
            quotesDao.getQuote()
                .map { quoteEntity -> Resource.Success(quoteEntity.toQuote()) }
                .collect { resource ->
                    emit(resource)
                }
        }
    }


    override suspend fun insertSentQuote(
        quote: Quote,
        dateTime: String
    ): Resource<Unit> {
        return safeDbCall {
            sentQuotesDao.insertQuote(quote.toSentQuoteEntity(dateTime))
        }
    }

    override suspend fun insetQuote(quote: Quote): Resource<Unit> {
        return safeDbCall {
            quotesDao.insertQuote(quote.toQuoteEntity())
        }
    }

    override fun getQuotes(): Flow<Resource<List<Quote>>> = flow {
        safeDbCall {
            quotesDao.getQuotes()
                .map { quotes ->
                    Resource.Success(quotes.map { it.toQuote() })
                }
        }
    }

    override suspend fun insertQuoteSendingError(
        error: String,
        dateTime: String
    ): Resource<Unit> {
        return safeDbCall {
            errorsDao.insertError(ErrorsEntity(error, dateTime))
        }
    }
}