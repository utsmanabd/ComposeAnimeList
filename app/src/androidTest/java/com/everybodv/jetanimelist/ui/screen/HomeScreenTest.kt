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

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeAnimeList = Dummy.fakeAnimeList
    private val fakeQuery = "Blue"

    @Before
    fun setUp() {
        composeTestRule.setContent { 
            JetAnimeListTheme {
                HomeScreen(navigateToDetail = {})
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun animeItem_isDisplayed() {
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(
                R.string.image_desc, fakeAnimeList.anime.title)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeAnimeList.anime.title).assertIsDisplayed()
    }

    @Test
    fun searchQuery_isCorrect() {
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(
                R.string.search_field
            )
        ).performTextInput(fakeQuery)
        composeTestRule.onNodeWithText(fakeAnimeList.anime.title).assertIsDisplayed()
    }

    @Test
    fun searchQuery_isIncorrect() {
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(
                R.string.search_field
            )
        ).performTextInput(fakeQuery + "s")
        composeTestRule.onNodeWithText(fakeAnimeList.anime.title).assertDoesNotExist()
    }

}