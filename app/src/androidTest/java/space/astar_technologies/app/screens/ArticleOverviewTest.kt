package space.astar_technologies.app.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import space.astar_technologies.app.mock.MockDataSource
import space.astar_technologies.app.navigation.NewsNavRoute
import space.astar_technologies.app.navigation.RootNavController
import space.astar_technologies.app.testingModule

class ArticleOverviewTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var controller: TestNavHostController

    @Before
    fun before() {
        stopKoin()

        GlobalContext.startKoin {
            androidContext(composeTestRule.activity.applicationContext)
            modules(testingModule)
        }

        composeTestRule.setContent {
            controller = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }

            RootNavController(controller)
        }
    }

    @Test
    fun `Article Overview`() {
        Assert.assertEquals(NewsNavRoute.Overview.route, controller.currentDestination?.route)

        for (article in MockDataSource.articles) {
            composeTestRule.onNodeWithText(article.title).assertIsDisplayed()
        }
    }
}