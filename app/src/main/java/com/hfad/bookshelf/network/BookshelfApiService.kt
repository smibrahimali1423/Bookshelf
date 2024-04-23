package com.hfad.bookshelf.network

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface BookshelfApiService {
    @GET("volumes")
    suspend fun getBookDetails(@Query("q") query: String, @Query("maxResults") maxResults: Int): BookResponse
}