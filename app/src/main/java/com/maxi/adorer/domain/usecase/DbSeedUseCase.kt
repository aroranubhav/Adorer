package com.maxi.adorer.domain.usecase

import com.maxi.adorer.common.Resource
import com.maxi.adorer.domain.repository.QuotesRepository
import javax.inject.Inject

class DbSeedUseCase @Inject constructor(
    private val repository: QuotesRepository
) {

    suspend operator fun invoke(fileName: String): Resource<Unit> =
        repository.seedDb(fileName)
}