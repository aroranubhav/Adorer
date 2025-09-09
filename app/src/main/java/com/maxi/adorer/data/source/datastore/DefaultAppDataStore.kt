package com.maxi.adorer.data.source.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.maxi.adorer.data.common.Constants.APP_PREFERENCES
import com.maxi.adorer.data.common.Constants.CURRENT_QUOTE
import com.maxi.adorer.data.common.Constants.DB_SEEDING_DONE
import com.maxi.adorer.domain.model.Quote
import com.maxi.adorer.domain.source.datastore.AppDatastore
import kotlinx.coroutines.flow.first

class DefaultAppDataStore(
    private val context: Context
) : AppDatastore {

    private val Context.dataStore by preferencesDataStore(name = APP_PREFERENCES)
    private val dbSeedingDone = booleanPreferencesKey(DB_SEEDING_DONE)
    private val currentQuote = stringPreferencesKey(CURRENT_QUOTE)

    private val gson = Gson()

    override suspend fun markDbSeedingDone() {
        context.dataStore.edit { prefs ->
            prefs[dbSeedingDone] = true
        }
    }

    override suspend fun checkDbSeedingDone(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[dbSeedingDone] ?: false
    }

    override suspend fun saveCurrentQuote(quote: Quote?) {
        val json = quote?.let {
            gson.toJson(quote)
        } ?: ""
        context.dataStore.edit { prefs ->
            prefs[currentQuote] = json
        }
    }

    override suspend fun getCurrentQuote(): Quote? {
        val prefs = context.dataStore.data.first()
        return prefs[currentQuote]?.let {
            gson.fromJson(it, Quote::class.java)
        }
    }
}




