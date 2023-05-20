package com.everybodv.jetanimelist

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.everybodv.jetanimelist.data.FakeAnimeDataSource
import com.everybodv.jetanimelist.ui.JetAnimeListApp
import com.everybodv.jetanimelist.ui.navigation.NavigationScreen
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetAnimeListAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetAnimeListTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetAnimeListApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationScreen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("AnimeList").performScrollToIndex(9)
        composeTestRule.onNodeWithText(FakeAnimeDataSource.anime[9].title).performClick()
        navController.assertCurrentRouteName(NavigationScreen.Detail.route)
        composeTestRule.onNodeWithText(FakeAnimeDataSource.anime[9].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.watchlist).performClick()
        navController.assertCurrentRouteName(NavigationScreen.WatchList.route)
        composeTestRule.onNodeWithContentDescription("about_page").performClick()
        navController.assertCurrentRouteName(NavigationScreen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.home).performClick()
        navController.assertCurrentRouteName(NavigationScreen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("AnimeList").performScrollToIndex(9)
        composeTestRule.onNodeWithText(FakeAnimeDataSource.anime[9].title).performClick()
        navController.assertCurrentRouteName(NavigationScreen.Detail.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.arrow_back)).performClick()
        navController.assertCurrentRouteName(NavigationScreen.Home.route)
    }
}
