package com.everybodv.jetanimelist.data

data class Anime (
    val id: Long,
    val title: String,
    val status: String,
    val episode: Int,
    val genre: String,
    val rating: String,
    val description: String,
    val imageUrl: String
)