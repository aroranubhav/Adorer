package com.maxi.adorer.data.source.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.ID
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.QUOTE
import com.maxi.adorer.data.source.db.util.LocalConstants.Tables.QUOTES
import com.maxi.adorer.domain.model.Quote

@Entity(QUOTES)
data class QuotesEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID)
    val id: String,
    @ColumnInfo(QUOTE)
    val quote: String
)

fun QuotesEntity.toQuote(): Quote =
    Quote(id, quote)
