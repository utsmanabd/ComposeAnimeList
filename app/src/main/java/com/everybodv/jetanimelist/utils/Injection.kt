package com.everybodv.jetanimelist.utils

import com.everybodv.jetanimelist.data.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}