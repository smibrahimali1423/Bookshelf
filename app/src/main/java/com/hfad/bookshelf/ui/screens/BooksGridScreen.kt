import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hfad.bookshelf.R
import com.hfad.bookshelf.network.ImageLinks
import com.hfad.bookshelf.network.Item import com.hfad.bookshelf.network.VolumeInfo
import com.hfad.bookshelf.ui.screens.BookItem
import com.hfad.bookshelf.ui.screens.BookshelfUiState
import com.hfad.bookshelf.ui.screens.ErrorScreen
import com.hfad.bookshelf.ui.screens.LoadingScreen

@Composable
fun BooksGridScreen(
    bookshelfUiState: BookshelfUiState,
    onItemClick: (Item) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)) {
    when (bookshelfUiState) {
        is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookshelfUiState.Success -> BookGridLoadedScreen(
            bookDetailsList = bookshelfUiState.items,
            onItemClick = onItemClick )
        is BookshelfUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}
@Composable
fun BookGridLoadedScreen(bookDetailsList: List<Item>, onItemClick: (Item) -> Unit) {
    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(bookDetailsList, key = { bookDetails -> bookDetails.id }) { bookDetails ->
            GridItem(bookDetails = bookDetails, onItemClick = onItemClick)
        }
    }
}


@Composable
fun GridItem(bookDetails: Item, onItemClick: (Item) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable { onItemClick(bookDetails) }
    ) {
        BookItem(bookDetails = bookDetails)
    }
}

@Composable
fun BookItem(bookDetails: Item) {
    Surface(
        color = MaterialTheme.colorScheme.surface, // Use the surface color from the theme
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
    )
    {
        Column(
            modifier = Modifier
                .padding(16.dp)

        ) {
            // Display thumbnail image
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(
                        bookDetails.volumeInfo.imageLinks.thumbnail.replace("http:", "https:")
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.broken_image_24px),
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display book details
            Text(
                text = bookDetails.volumeInfo.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            )

            Text(
                text = bookDetails.volumeInfo.authors.joinToString(", ") { it } ?: "Unknown author",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            )
        }
    }
}
