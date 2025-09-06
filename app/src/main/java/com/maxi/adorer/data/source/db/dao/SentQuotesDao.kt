package com.maxi.adorer.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.maxi.adorer.data.source.db.entity.SentQuotesEntity

@Dao
interface SentQuotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuote(quote: SentQuotesEntity): Long
}