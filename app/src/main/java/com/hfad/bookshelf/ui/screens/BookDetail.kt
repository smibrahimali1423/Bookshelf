package com.hfad.bookshelf.ui.screens

import BookGridLoadedScreen
import BookItem
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.hfad.bookshelf.network.Item
import com.hfad.bookshelf.network.VolumeInfo


@Composable
fun BookItem(
    bookshelfUiState: BookshelfUiState,
    item: Item,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (bookshelfUiState) {
        is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookshelfUiState.Success -> BookItemLoaded(bookDetails = item)
        is BookshelfUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }


}
@Composable
fun BookItemLoaded(bookDetails: Item) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState())

    ) {
        // Display thumbnail image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(start = 26.dp, end = 26.dp, bottom = 8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(bookDetails.volumeInfo.imageLinks.thumbnail.replace("http:", "https:"))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.broken_image_24px),
                placeholder = if (bookDetails.volumeInfo.imageLinks.thumbnail.isNotEmpty()) {
                    painterResource(R.drawable.loading_img)
                } else {
                    painterResource(R.drawable.broken_image_24px)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Display book details
        Text(
            text = bookDetails.volumeInfo.title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth() // Ensure the title takes up the full width

        )

        Text(
            text = bookDetails.volumeInfo.authors.joinToString(", ") { it },
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = bookDetails.volumeInfo.description ?: "",
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                ),
                modifier = Modifier.fillMaxSize()

            )
        }


        Text(
            text = "Published Date: ${bookDetails.volumeInfo.publishedDate }",
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Publisher: ${bookDetails.volumeInfo.publisher }",
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BookItemPreview() {
    val mockBook = Item(
        id = "1",
        selfLink = "https://example.com/book/1",
        volumeInfo = VolumeInfo(
            title = "Sample Book",
            authors = listOf("John Doe"),
            description = "This is a sample book description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            publishedDate = "2022-01-01",
            publisher = "Sample Publisher",
            imageLinks = ImageLinks(thumbnail = "")
        )
    )

    BookItem(bookDetails = mockBook)
}

