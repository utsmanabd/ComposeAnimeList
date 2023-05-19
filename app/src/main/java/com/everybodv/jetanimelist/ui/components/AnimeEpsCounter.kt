package com.everybodv.jetanimelist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.everybodv.jetanimelist.R
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme

@Composable
fun AnimeEpsCounter(
    animeId: Long,
    epsCount: Int,
    totalEps: Int,
    onEpsIncreased: (Long) -> Unit,
    onEpsDecreased: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .size(width = 150.dp, height = 40.dp)
            .padding(4.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(size = 5.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            contentColor = MaterialTheme.colors.primary,
            color = Color.Transparent,
            modifier = Modifier.size(30.dp)
        ) {
            Text(
                text = "—",
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onEpsDecreased(animeId)
                    }
            )
        }
        Text(
            text = stringResource(id = R.string.eps_count, epsCount, totalEps),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f).testTag("count")
        )
        Surface(
            shape = RoundedCornerShape(size = 5.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            contentColor = MaterialTheme.colors.primary,
            color = Color.Transparent,
            modifier = Modifier.size(30.dp)
        ) {
            Text(
                text = "＋",
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onEpsIncreased(animeId)
                    }
            )
        }
    }
}

@Preview
@Composable
fun AnimeEpsCounterPreview() {
    JetAnimeListTheme {
        AnimeEpsCounter(animeId = 1, epsCount = 2, totalEps = 12, onEpsIncreased = { }, onEpsDecreased = { })
    }
}