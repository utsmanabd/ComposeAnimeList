package com.everybodv.jetanimelist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.everybodv.jetanimelist.R
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme

@Composable
fun AnimeItem(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(12.dp)) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.image_desc, title),
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight().shadow(8.dp).clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AnimeItemPreview() {
    JetAnimeListTheme {
        AnimeItem(
            imageUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx137822-4dVWMSHLpGf8.png",
            title = "Blue Lock"
        )
    }
}