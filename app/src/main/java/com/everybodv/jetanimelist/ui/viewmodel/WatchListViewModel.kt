package com.everybodv.jetanimelist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everybodv.jetanimelist.data.AnimeRepository
import com.everybodv.jetanimelist.ui.screen.WatchListState
import com.everybodv.jetanimelist.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WatchListViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<WatchListState>>(UiState.Loading)
    val uiState: StateFlow<UiState<WatchListState>>
        get() = _uiState

    fun getAddedWatchList() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedWatchList()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { watchList ->
                _uiState.value = UiState.Success(WatchListState(watchList))
            }
        }
    }

    fun updateEpisodesWatched(animeId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateWatchList(animeId, count)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { isUpdated ->
                if (isUpdated) getAddedWatchList()
            }
        }
    }
}