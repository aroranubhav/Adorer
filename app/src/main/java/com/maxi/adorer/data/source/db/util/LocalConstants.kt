package com.maxi.adorer.data.source.db.util

object LocalConstants {

    const val QUOTES_DATABASE = "quotes_db"

    object Tables {
        const val QUOTES = "quotes"
        const val SENT_QUOTES = "sent_quotes"
        const val SENDING_ERRORS = "sending_errors"
    }

    object Columns {
        const val ID = "id"
        const val QUOTE = "quote"
        const val DATE_TIME = "date_time"
        const val ERROR = "error"
    }
}