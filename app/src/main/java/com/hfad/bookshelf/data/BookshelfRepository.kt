package com.hfad.bookshelf.data

import androidx.lifecycle.viewmodel.compose.viewModel
import com.hfad.bookshelf.network.BookshelfApiService
import com.hfad.bookshelf.network.Item
import com.hfad.bookshelf.ui.screens.BookshelfViewModel

interface BookshelfRepository {
    suspend fun getBooksList(query: String): List<Item>
}

class NetworkBookshelfRepository(private val bookshelfApiService: BookshelfApiService): BookshelfRepository {
    override suspend fun getBooksList(query: String): List<Item> = (bookshelfApiService.getBookDetails(query, 12)).items

}