package com.everybodv.jetanimelist.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.everybodv.jetanimelist.Dummy
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeAnimeList = Dummy.fakeAnimeList

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetAnimeListTheme {
                DetailContent(
                    title = fakeAnimeList.anime.title,
                    imageUrl = fakeAnimeList.anime.imageUrl,
                    status = fakeAnimeList.anime.status,
                    episode = fakeAnimeList.anime.episode,
                    genre = fakeAnimeList.anime.genre,
                    rating = fakeAnimeList.anime.rating,
                    description = fakeAnimeList.anime.description,
                    onBackClick = {},
                    onAddToWatchList = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeAnimeList.anime.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeAnimeList.anime.rating).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeAnimeList.anime.episode.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeAnimeList.anime.status).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeAnimeList.anime.genre).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeAnimeList.anime.description).assertIsDisplayed()
    }
}