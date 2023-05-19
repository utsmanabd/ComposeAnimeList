package com.everybodv.jetanimelist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.everybodv.jetanimelist.R
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme
import com.everybodv.jetanimelist.ui.theme.Shapes

@Composable
fun WatchListItem(
    animeId: Long,
    imageUrl: String,
    title: String,
    count: Int,
    totalEps: Int,
    onEpsCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.image_desc, title),
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
                .align(Alignment.CenterVertically)
                .padding(bottom = 8.dp)
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1.0f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold)
            )
            Text(
                text = stringResource(id = R.string.eps_watched),
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Normal)
            )
            AnimeEpsCounter(
                animeId = animeId,
                epsCount = count,
                totalEps = totalEps,
                onEpsIncreased = {
                    onEpsCountChanged(
                        animeId,
                        if (count == totalEps) count else count + 1
                    )
                },
                onEpsDecreased = {
                    onEpsCountChanged(
                        animeId,
                        if (count == 1) count else count - 1
                    )
                }
            )
        }
        Button(
            onClick = { onEpsCountChanged(animeId, 0) },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.remove_watchlist, title)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WatchListItemPreview() {
    JetAnimeListTheme {
        WatchListItem(
            animeId = 1,
            imageUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx137822-4dVWMSHLpGf8.png",
            title = "Blue Lock",
            count = 4,
            totalEps = 12,
            onEpsCountChanged = { _, _ -> },
        )
    }
}