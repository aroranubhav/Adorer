package com.maxi.adorer.domain.source.datastore

interface AppDatastore {

    suspend fun checkDbSeedingDone(): Boolean

    suspend fun markDbSeedingDone()
}