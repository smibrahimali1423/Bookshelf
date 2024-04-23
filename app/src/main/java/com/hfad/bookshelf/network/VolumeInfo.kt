package com.hfad.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    var authors: List<String> = listOf("Not known", "not known"),
    var description: String = "Not available",
    var imageLinks: ImageLinks = ImageLinks(),
    var language: String = "Not known",
    var pageCount: Int = 0,
    var publishedDate: String = "Unknown",
    var publisher: String = "Not available",
    var title: String
)