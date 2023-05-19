package com.everybodv.jetanimelist.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.everybodv.jetanimelist.R
import com.everybodv.jetanimelist.data.WatchListAnime
import com.everybodv.jetanimelist.ui.components.WatchListItem
import com.everybodv.jetanimelist.ui.viewmodel.ViewModelFactory
import com.everybodv.jetanimelist.ui.viewmodel.WatchListViewModel
import com.everybodv.jetanimelist.utils.Injection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.everybodv.jetanimelist.utils.UiState

@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedWatchList()
            }
            is UiState.Success -> {
                WatchListContent(
                    state = uiState.data,
                    onEpsCountChanged = { animeId, count ->
                        viewModel.updateEpisodesWatched(animeId, count)
                    },
                )
            }
            is UiState.Error -> {
                Log.e("WatchListScreen: ", stringResource(id = R.string.failed_to_load))
            }
        }
    }
}

@Composable
fun WatchListContent(
    modifier: Modifier = Modifier,
    state: WatchListState,
    onEpsCountChanged: (id: Long, count: Int) -> Unit,
) {
    val totalWatchList = state.watchList.count()
    
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = MaterialTheme.colors.primaryVariant) {
            Text(
                text = stringResource(id = R.string.watchlist),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        if (totalWatchList == 0) {
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_list),
                    contentDescription = stringResource(id = R.string.empty_list),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(220.dp)
                )
                Text(
                    text = stringResource(id = R.string.empty_list),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.total_watchlist, totalWatchList),
                modifier = Modifier.padding(8.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.watchList, key = { it.anime.id }){ item ->
                    WatchListItem(
                        animeId = item.anime.id,
                        imageUrl = item.anime.imageUrl,
                        title = item.anime.title,
                        count = item.count,
                        totalEps = item.anime.episode,
                        onEpsCountChanged = onEpsCountChanged
                    )
                    Divider()
                }
            }
        }
    }
}

data class WatchListState(
    val watchList: List<WatchListAnime>
)