package com.maxi.adorer.data.source.filesystem

import android.content.Context
import com.google.gson.Gson
import com.maxi.adorer.data.source.db.entity.Quotes
import com.maxi.adorer.domain.model.Quote
import com.maxi.adorer.domain.source.filesystem.FileReader

class DefaultFileReader(
    private val context: Context
) : FileReader {

    override suspend fun readQuotesFromFile(fileName: String): List<Quote> {
        val json = context.assets.open(fileName)
            .bufferedReader().use { it.readText() }

        return parseJson(json)
    }

    private fun parseJson(json: String): List<Quote> {
        val quotesWrapper = Gson().fromJson(json, Quotes::class.java)
        return quotesWrapper.quotes.map { Quote(it.id, it.quote) }
    }
}