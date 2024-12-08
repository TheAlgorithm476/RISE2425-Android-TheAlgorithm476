package space.astar_technologies.app.navigation

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import space.astar_technologies.app.testingModule

class NavigationTest {
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
    fun `Verify Start Position`() {
        Assert.assertEquals(NewsNavRoute.Overview.route, controller.currentDestination?.route)
    }

    @Test
    fun `Click Bottom Bar -- Vehicles`() {
        composeTestRule.onNodeWithText(NavRoute.Vehicles.title)
            .performClick()

        Assert.assertEquals(VehiclesNavRoute.Overview.route, controller.currentDestination?.route)
    }

    @Test
    fun `Click Bottom Bar -- Launches`() {
        composeTestRule.onNodeWithText(NavRoute.Launches.title)
            .performClick()

        Assert.assertEquals(LaunchesNavRoute.Overview.route, controller.currentDestination?.route)
    }
}