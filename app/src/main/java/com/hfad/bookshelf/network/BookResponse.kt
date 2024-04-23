package com.hfad.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    var items: List<Item>,
    var kind: String,
    var totalItems: Int
)