package com.example.mgh01.techtask.models

data class MarvelComicResult(
    val title: String? = null,
    val urls: List<MarvelComicUrl>? = null,
    val thumbnail: MarvelComicThumbnail? = null)