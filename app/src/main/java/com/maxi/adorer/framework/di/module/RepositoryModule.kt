package com.maxi.adorer.framework.di.module

import com.maxi.adorer.data.repository.DefaultQuotesRepository
import com.maxi.adorer.data.source.db.dao.QuotesDao
import com.maxi.adorer.data.source.db.dao.SentQuotesDao
import com.maxi.adorer.domain.repository.QuotesRepository
import com.maxi.adorer.domain.source.datastore.AppDatastore
import com.maxi.adorer.domain.source.filesystem.FileReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQuotesRepository(
        quotesDao: QuotesDao,
        sentQuotesDao: SentQuotesDao,
        fileReader: FileReader,
        datastore: AppDatastore
    ): QuotesRepository = DefaultQuotesRepository(
        quotesDao,
        sentQuotesDao,
        fileReader,
        datastore
    )

}