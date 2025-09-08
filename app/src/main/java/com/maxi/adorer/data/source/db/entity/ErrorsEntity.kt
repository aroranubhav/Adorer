package com.maxi.adorer.data.source.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.DATE_TIME
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.ERROR
import com.maxi.adorer.data.source.db.util.LocalConstants.Columns.ID
import com.maxi.adorer.data.source.db.util.LocalConstants.Tables.SENDING_ERRORS

@Entity(SENDING_ERRORS)
data class ErrorsEntity(
    @ColumnInfo(ERROR)
    val error: String,
    @ColumnInfo(DATE_TIME)
    val dateTime: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID)
    val id: Int = 0
)
