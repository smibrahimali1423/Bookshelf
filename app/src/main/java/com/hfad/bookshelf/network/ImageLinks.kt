package com.hfad.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class ImageLinks(
    var smallThumbnail: String = "",
    var thumbnail: String = ""
)