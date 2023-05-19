package com.everybodv.jetanimelist.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.everybodv.jetanimelist.Dummy
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.everybodv.jetanimelist.R

class WatchListContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeAnimeList = Dummy.fakeAnimeList
    private val state = WatchListState(listOf(fakeAnimeList))

    @Before
    fun setUp() {
        composeTestRule.setContent { 
            JetAnimeListTheme {
                WatchListContent(state = state, onEpsCountChanged = {_,_->})
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun watchList_isDisplayed() {
        composeTestRule.onNodeWithText(fakeAnimeList.anime.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.eps_count, fakeAnimeList.count, fakeAnimeList.anime.episode
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText("＋").assertIsDisplayed()
        composeTestRule.onNodeWithText("—").assertIsDisplayed()
    }

    @Test
    fun increaseEpisode_isClickable() {
        composeTestRule.onNodeWithText("＋").assertHasClickAction()
    }

    @Test
    fun decreaseEpisode_isClickable() {
        composeTestRule.onNodeWithText("—").assertHasClickAction()
    }


}