package com.maxi.adorer.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maxi.adorer.data.source.db.entity.QuotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuotes(quotes: List<QuotesEntity>): List<Long>

    @Query(
        """SELECT * FROM quotes 
        WHERE id NOT IN (SELECT id FROM sent_quotes)
        LIMIT 1
    """
    )
    fun getQuote(): Flow<QuotesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuote(quote: QuotesEntity): Long

    @Query("SELECT * FROM quotes")
    fun getQuotes(): Flow<List<QuotesEntity>>
}