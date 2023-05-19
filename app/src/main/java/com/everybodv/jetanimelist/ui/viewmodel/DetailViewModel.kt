package com.everybodv.jetanimelist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everybodv.jetanimelist.data.Anime
import com.everybodv.jetanimelist.data.AnimeRepository
import com.everybodv.jetanimelist.data.WatchListAnime
import com.everybodv.jetanimelist.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: AnimeRepository) : ViewModel(){

    private val _uiState = MutableStateFlow<UiState<WatchListAnime>>(UiState.Loading)
    val uiState: StateFlow<UiState<WatchListAnime>>
        get() = _uiState

    fun getAnimeById(animeId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAnimeById(animeId))
        }
    }

    fun addToWatchList(anime: Anime, count: Int) {
        viewModelScope.launch {
            repository.updateWatchList(anime.id, count)
        }
    }
}