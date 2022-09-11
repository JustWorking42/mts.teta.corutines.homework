package ru.ermolnik.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import ru.ermolnik.news.model.NewsModel

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val swipeRefreshState = SwipeRefreshState(false)
    val state = viewModel.state.collectAsState()
    SwipeRefresh(
        state = swipeRefreshState, onRefresh = {
            viewModel.refresh()
        }, modifier = Modifier.background(
            Color.White
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (state.value) {
                is NewsState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }
                is NewsState.Error -> {
                    ErrorMessage(
                        (state.value as NewsState.Error).throwable.message.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) { viewModel.refresh() }
                }
                is NewsState.Content -> {
                    NewsList(items = (state.value as NewsState.Content).newsModel)
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(message: String, modifier: Modifier, errorAction: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Red
            )
        )
        Button(
            onClick = errorAction,
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Update",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun NewsList(items: List<NewsModel>) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items
        ) {
            NewsItem(model = it)
        }
    }
}

@Composable
fun NewsItem(model: NewsModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(
            corner = CornerSize(16.dp)
        )
    ) {
        Row(
            modifier = Modifier.background(colorResource(id = R.color.gray))
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 6.dp,
                        bottom = 2.dp
                    ), text = model.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        bottom = 6.dp
                    ), text = model.description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )
            }

        }
    }
}
