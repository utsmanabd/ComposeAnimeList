package com.everybodv.jetanimelist.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.everybodv.jetanimelist.R
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme
import com.everybodv.jetanimelist.ui.viewmodel.DetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.everybodv.jetanimelist.ui.viewmodel.ViewModelFactory
import com.everybodv.jetanimelist.utils.Injection
import com.everybodv.jetanimelist.utils.UiState

@Composable
fun DetailScreen(
    animeId: Long,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
    addToWatchList: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAnimeById(animeId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    title = data.anime.title,
                    imageUrl = data.anime.imageUrl,
                    status = data.anime.status,
                    episode = data.anime.episode,
                    genre = data.anime.genre,
                    rating = data.anime.rating,
                    description = data.anime.description,
                    onBackClick = navigateBack,
                    onAddToWatchList = { count ->
                        viewModel.addToWatchList(data.anime, count)
                        addToWatchList()
                    }
                )
            }
            is UiState.Error -> {
                Log.e("DetailScreen: ", stringResource(id = R.string.failed_to_load))
            }
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    count: Int = 1,
    title: String,
    imageUrl: String,
    status: String,
    episode: Int,
    genre: String,
    rating: String,
    description: String,
    onBackClick: () -> Unit,
    onAddToWatchList: (count: Int) -> Unit,
) {
    val epsCount by rememberSaveable { mutableStateOf(count) }
    val titleStyle = MaterialTheme.typography.subtitle1.copy(fontSize = 12.sp, fontWeight = FontWeight.Light)

    Column(modifier = modifier) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.arrow_back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
        }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(start = 28.dp, bottom = 16.dp, top = 8.dp, end = 28.dp)
            )
            Row(modifier = modifier.padding(horizontal = 28.dp)) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(id = R.string.image_desc),
                    modifier = Modifier
                        .width(160.dp)
                        .fillMaxHeight()
                        .shadow(4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    alignment = Alignment.Center
                )
                Column(modifier = modifier.padding(start = 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.average_score),
                        style = titleStyle
                    )
                    Row(modifier = modifier.padding(top = 4.dp)) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(id = R.string.average_score),
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(30.dp)
                        )
                        Text(
                            text = rating,
                            fontSize = 22.sp
                        )
                    }
                    Column(modifier = modifier.padding(top = 8.dp)) {
                        Text(
                            text = stringResource(id = R.string.total_eps),
                            style = titleStyle
                        )
                        Text(
                            text = episode.toString(),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Column(modifier = modifier.padding(top = 8.dp)) {
                        Text(
                            text = stringResource(id = R.string.status),
                            style = titleStyle
                        )
                        Text(
                            text = status,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Column(modifier = modifier.padding(top = 8.dp)) {
                        Text(
                            text = stringResource(id = R.string.genre),
                            style = titleStyle
                        )
                        Text(
                            text = genre,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            Column(modifier = modifier.padding(
                start = 28.dp, top = 16.dp, end = 28.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.description),
                    style = titleStyle
                )
                Text(text = description, modifier = Modifier.padding(top = 4.dp))
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = { onAddToWatchList(epsCount)},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)) {
                Text(text = "＋  " + stringResource(id = R.string.add_to_watchlist))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    JetAnimeListTheme {
        DetailContent(
            title = "Blue Lock",
            imageUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx137822-4dVWMSHLpGf8.png",
            status = "Released",
            episode = 12,
            genre = "Sports",
            rating = "81%",
            description = "After a disastrous defeat at the 2018 World Cup, Japan's team struggles to regroup. But what's missing? An absolute Ace Striker, who can guide them to the win. The Japan Football Union is hell-bent on creating a striker who hungers for goals and thirsts for victory, and who can be the decisive instrument in turning around a losing match...and to do so, they've gathered 300 of Japan's best and brightest youth players. Who will emerge to lead the team...and will they be able to out-muscle and out-ego everyone who stands in their way?",
            onBackClick = {},
            onAddToWatchList = {}
        )
    }
}