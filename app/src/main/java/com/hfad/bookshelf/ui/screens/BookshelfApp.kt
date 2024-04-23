package com.hfad.bookshelf.ui.screens

import BooksGridScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hfad.bookshelf.R




enum class BookshelfScreen(val title: String) {
    Start("Bookshelf"),
    BooksGrid("Books found: "),
    BookDetail("Book details")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfAppBar(
    currentScreen: BookshelfScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}


@Composable
fun BookshelfApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookshelfScreen.valueOf(
        backStackEntry?.destination?.route ?: BookshelfScreen.Start.name
    )

    val viewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)


    Scaffold(
        topBar = {
            BookshelfAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }


    ){
        innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BookshelfScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BookshelfScreen.Start.name)
            {
                StartScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onSearch = { userInput ->
                        viewModel.setSearchButton(userInput)
                        viewModel.getBooks()
                    navController.navigate(BookshelfScreen.BooksGrid.name)}

                )
            }

            composable(route = BookshelfScreen.BooksGrid.name) {
                BooksGridScreen(bookshelfUiState = viewModel.bookshelfUiState,
                    onItemClick = { selectedItem ->
                        viewModel.setSelectedItemOnNewScreen(selectedItem)
                        navController.navigate(BookshelfScreen.BookDetail.name)
                    },
                    retryAction = viewModel::getBooks
                )
            }

            composable(route = BookshelfScreen.BookDetail.name)
            {
                BookItem(
                    bookshelfUiState = viewModel.bookshelfUiState,
                    item = viewModel.selectedItem ,
                    retryAction = viewModel::getBooks
                )
            }
        }



    }
}




