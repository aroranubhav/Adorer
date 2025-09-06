package com.maxi.adorer.data.source.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.DATE_TIME
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.ID
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.QUOTE
import com.maxi.adorer.data.source.db.util.LocalConstants.Tables.SENT_QUOTES

@Entity(SENT_QUOTES)
data class SentQuotesEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID)
    val id: String,
    @ColumnInfo(QUOTE)
    val quote: String,
    @ColumnInfo(DATE_TIME)
    val dateTime: String
)
