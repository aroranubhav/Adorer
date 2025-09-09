package com.maxi.adorer.domain.usecase

import com.maxi.adorer.common.Constants.GAP_IN_RETRIES
import com.maxi.adorer.common.Constants.MAX_DB_SEEDING_RETRY_COUNT
import com.maxi.adorer.common.Resource
import com.maxi.adorer.domain.repository.QuotesRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DbSeedUseCase @Inject constructor(
    private val repository: QuotesRepository
) {

    suspend operator fun invoke(fileName: String): Resource<Unit> =
        seedWithRetry(fileName)

    private suspend fun seedWithRetry(
        fileName: String,
        maxRetries: Int = MAX_DB_SEEDING_RETRY_COUNT,
        delayMillis: Long = GAP_IN_RETRIES
    ): Resource<Unit> {
        repeat(maxRetries - 1) {
            val result = repository.seedDb(fileName)
            if (result is Resource.Success) return result
            delay(delayMillis * (it + 1))
        }
        return repository.seedDb(fileName)
    }
}
//TODO: update app to use Kotlin.Result in dao's?,
// also add a check to see if it fails after max retries to show an error?
