package com.hfad.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    var id: String,
    var selfLink: String,
    var volumeInfo: VolumeInfo
)