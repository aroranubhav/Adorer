package com.maxi.adorer.presentation.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxi.adorer.common.DispatcherProvider
import com.maxi.adorer.common.Resource
import com.maxi.adorer.domain.source.datastore.AppDatastore
import com.maxi.adorer.domain.usecase.DbSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val seedUseCase: DbSeedUseCase,
    private val datastore: AppDatastore,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState get() = _uiState

    fun seedDb(fileName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            if (!datastore.checkDbSeedingDone()) {
                val result = seedUseCase.invoke(fileName)
                if (result is Resource.Success) {
                    _uiState.value = true
                }
            }
        }
    }
}