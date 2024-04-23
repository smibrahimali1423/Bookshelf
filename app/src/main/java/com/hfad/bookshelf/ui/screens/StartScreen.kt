package com.hfad.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.bookshelf.R

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
) {
    var userInput by remember { mutableStateOf("") }

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(64.dp))
        Image(
            painter = painterResource(id = R.drawable.books),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.padding(12.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.padding(6.dp))

        Text(
            text = "Thousands of Books, just a search away!",
            style = TextStyle(
                fontSize = 17.sp
            )
        )

        TextField(
            value = userInput,
            onValueChange = {
                userInput = it
                 // Call the callback function to update ViewModel
            },
            singleLine = true,
            label = { Text("Search for books") },
            leadingIcon = { Image(painter = painterResource(id = R.drawable.book_5_24px), contentDescription = null) },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(12.dp)
                .width(330.dp)
        )

        Button(
            onClick = { onSearch(userInput) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.width(150.dp)
        ) {
            Text(text = "Search")
        }
    }
}

@Preview (showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {}
}