package com.maxi.adorer.framework.di.module

import android.content.Context
import androidx.room.Room
import com.maxi.adorer.common.DefaultDispatcherProvider
import com.maxi.adorer.common.DispatcherProvider
import com.maxi.adorer.data.source.datastore.DefaultAppDataStore
import com.maxi.adorer.data.source.db.QuotesDatabase
import com.maxi.adorer.data.source.db.dao.ErrorsDao
import com.maxi.adorer.data.source.db.dao.QuotesDao
import com.maxi.adorer.data.source.db.dao.SentQuotesDao
import com.maxi.adorer.data.source.db.util.LocalConstants.QUOTES_DATABASE
import com.maxi.adorer.data.source.filesystem.DefaultFileReader
import com.maxi.adorer.domain.repository.SmsSender
import com.maxi.adorer.domain.source.datastore.AppDatastore
import com.maxi.adorer.domain.source.filesystem.FileReader
import com.maxi.adorer.framework.sms.DefaultSmsSender
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuotesDatabase(
        @ApplicationContext context: Context
    ): QuotesDatabase =
        Room
            .databaseBuilder(
                context,
                QuotesDatabase::class.java,
                QUOTES_DATABASE
            ).build()

    @Provides
    @Singleton
    fun provideQuotesDao(
        database: QuotesDatabase
    ): QuotesDao =
        database.getQuotesDao()

    @Provides
    @Singleton
    fun provideSentQuotesDao(
        database: QuotesDatabase
    ): SentQuotesDao =
        database.getSentQuotesDao()

    @Provides
    @Singleton
    fun provideErrorsDao(
        database: QuotesDatabase
    ): ErrorsDao =
        database.getErrorsDao()

    @Provides
    @Singleton
    fun provideFileReader(
        @ApplicationContext context: Context
    ): FileReader =
        DefaultFileReader(context)

    @Provides
    @Singleton
    fun provideAppDataStore(
        @ApplicationContext context: Context
    ): AppDatastore =
        DefaultAppDataStore(context)

    @Provides
    @Singleton
    fun provideSmsSender(
        @ApplicationContext context: Context
    ): SmsSender =
        DefaultSmsSender(context)

    @Provides
    @Singleton
    fun provideDispatcherProvider():
            DispatcherProvider =
        DefaultDispatcherProvider()
}