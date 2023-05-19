package com.everybodv.jetanimelist.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everybodv.jetanimelist.data.AnimeRepository
import com.everybodv.jetanimelist.data.WatchListAnime
import com.everybodv.jetanimelist.utils.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<WatchListAnime>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<WatchListAnime>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String>
        get() = _query

    fun getAllAnime() {
        viewModelScope.launch {
            repository.getAllAnime()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun search(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.getAllAnime()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                _uiState.value = UiState.Success(repository.searchAnime(_query.value))
            }
        }

    }
}