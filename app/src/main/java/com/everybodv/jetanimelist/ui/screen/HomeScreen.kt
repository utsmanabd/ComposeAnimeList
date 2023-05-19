package com.everybodv.jetanimelist.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.everybodv.jetanimelist.data.WatchListAnime
import com.everybodv.jetanimelist.ui.components.AnimeItem
import com.everybodv.jetanimelist.ui.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.everybodv.jetanimelist.R
import com.everybodv.jetanimelist.ui.components.SearchBar
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme
import com.everybodv.jetanimelist.ui.viewmodel.ViewModelFactory
import com.everybodv.jetanimelist.utils.Injection
import com.everybodv.jetanimelist.utils.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllAnime()
            }
            is UiState.Success -> {
                HomeContent(
                    animeList = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier,
                    query = query,
                    onQueryChange = viewModel::search
                )
            }
            is UiState.Error -> {
                Log.e("HomeScreen: ", stringResource(id = R.string.failed_to_load))
            }
        }
    }
}

@Composable
fun HomeContent(
    animeList: List<WatchListAnime>,
    navigateToDetail: (Long) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = MaterialTheme.colors.primaryVariant) {
            Text(
                text = stringResource(id = R.string.home),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        SearchBar(
            query = query,
            onQueryChange = onQueryChange
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("AnimeList")
        ) {
            items(animeList) { animeList ->
                AnimeItem(
                    imageUrl = animeList.anime.imageUrl,
                    title = animeList.anime.title,
                    modifier = modifier.clickable { navigateToDetail(animeList.anime.id) }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    JetAnimeListTheme {
        HomeScreen(navigateToDetail = {})
    }
}