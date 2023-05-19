package com.everybodv.jetanimelist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AnimeRepository {

    private val watchListAnime = mutableListOf<WatchListAnime>()

    init {
        if (watchListAnime.isEmpty()) {
            FakeAnimeDataSource.anime.forEach { anime ->
                watchListAnime.add(WatchListAnime(anime, 0))
            }
        }
    }

    fun getAllAnime(): Flow<List<WatchListAnime>> {
        return flowOf(watchListAnime)
    }

    fun getAnimeById(animeId: Long): WatchListAnime {
        return watchListAnime.first {
            it.anime.id == animeId
        }
    }

    fun updateWatchList(animeId: Long, newCount: Int): Flow<Boolean> {
        val index = watchListAnime.indexOfFirst { it.anime.id == animeId }
        val result =
            if (index >= 0) {
                val watchList = watchListAnime[index]
                watchListAnime[index] = watchList.copy(anime = watchList.anime, count = newCount)
                true
            } else false
        return flowOf(result)
    }

    fun getAddedWatchList(): Flow<List<WatchListAnime>> {
        return getAllAnime().map { watchList->
            watchList.filter { anime ->
                anime.count != 0
            }
        }
    }

    fun searchAnime(query: String): List<WatchListAnime> {
        return watchListAnime.filter {
            it.anime.title.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }

}