package com.hfad.bookshelf.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hfad.bookshelf.BookshelfApplication
import com.hfad.bookshelf.data.BookshelfRepository
import com.hfad.bookshelf.network.Item
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(val items: List<Item>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookshelfViewModel(private val bookshelfRepository: BookshelfRepository): ViewModel() {


    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    private val _bookSearched = mutableStateOf("")
    val bookSearched: MutableState<String> = _bookSearched

    var books: List<Item> = emptyList()

    lateinit var selectedItem: Item

    fun setSelectedItemOnNewScreen(item: Item) {
        selectedItem = item
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }

    fun setSearchButton(userBookName: String) {
        _bookSearched.value = userBookName

    }

    fun getBooks() {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading

            bookshelfUiState = try {
                BookshelfUiState.Success(bookshelfRepository.getBooksList(_bookSearched.value)
                )
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }


}





