package com.maxi.adorer.domain.source.filesystem

import com.maxi.adorer.domain.model.Quote

interface FileReader {

    suspend fun readQuotesFromFile(fileName: String): List<Quote>
}