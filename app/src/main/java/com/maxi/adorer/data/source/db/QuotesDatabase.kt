package com.maxi.adorer.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxi.adorer.data.source.db.dao.ErrorsDao
import com.maxi.adorer.data.source.db.dao.QuotesDao
import com.maxi.adorer.data.source.db.dao.SentQuotesDao
import com.maxi.adorer.data.source.db.entity.ErrorsEntity
import com.maxi.adorer.data.source.db.entity.QuotesEntity
import com.maxi.adorer.data.source.db.entity.SentQuotesEntity

@Database(
    entities = [QuotesEntity::class, SentQuotesEntity::class, ErrorsEntity::class],
    version = 1,
    exportSchema = true
)
abstract class QuotesDatabase : RoomDatabase() {

    abstract fun getQuotesDao(): QuotesDao

    abstract fun getSentQuotesDao(): SentQuotesDao

    abstract fun getErrorsDao(): ErrorsDao
}