package space.astar_technologies.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import space.astar_technologies.app.navigation.RootNavController
import space.astar_technologies.app.network.NETWORK_MODULE
import space.astar_technologies.app.ui.theme.AstarLaunchAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidLogger()
                androidContext(this@MainActivity)

                modules(NETWORK_MODULE)
            }
        }

        setContent {
            KoinContext {
                AstarLaunchAppTheme {
                    RootNavController()
                }
            }
        }
    }

    companion object {
        var isOffline: Boolean = false
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AstarLaunchAppTheme {
        RootNavController()
    }
}