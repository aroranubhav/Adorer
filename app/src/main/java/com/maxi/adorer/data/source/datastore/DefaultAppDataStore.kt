package com.maxi.adorer.data.source.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.maxi.adorer.data.common.Constants.DB_SEEDING_DONE
import com.maxi.adorer.data.common.Constants.DB_SEED_DATA_STORE
import com.maxi.adorer.domain.source.datastore.AppDatastore
import kotlinx.coroutines.flow.first

class DefaultAppDataStore(
    private val context: Context
) : AppDatastore {

    private val Context.dataStore by preferencesDataStore(name = DB_SEED_DATA_STORE)
    private val dbSeedingDone = booleanPreferencesKey(DB_SEEDING_DONE)

    override suspend fun checkDbSeedingDone(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[dbSeedingDone] ?: false
    }

    override suspend fun markDbSeedingDone() {
        context.dataStore.edit { prefs ->
            prefs[dbSeedingDone] = true
        }
    }
}