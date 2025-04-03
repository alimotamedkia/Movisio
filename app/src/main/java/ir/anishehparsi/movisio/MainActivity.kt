package ir.anishehparsi.movisio

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.MovisioTheme
import com.orhanobut.hawk.Hawk
import com.ramcosta.composedestinations.DestinationsNavHost
import ir.anishehparsi.movisio.Logic.ExitDialog
import ir.anishehparsi.movisio.destinations.AccountUiDestination
import ir.anishehparsi.movisio.destinations.MovieDetailUiDestination
import ir.anishehparsi.movisio.destinations.MovieFavUiDestination
import ir.anishehparsi.movisio.destinations.MovieHomeUiDestination
import ir.anishehparsi.movisio.destinations.UserUiDestination


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            MovisioTheme {
                val navController = rememberNavController()
                val currentBackStateEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStateEntry?.destination?.route
                val title = when (currentRoute) {
                    MovieHomeUiDestination.route -> "Home"
                    MovieFavUiDestination.route -> "Favorite"
                    UserUiDestination.route -> "User Attention"
                    MovieDetailUiDestination.route->"Detail"
                    AccountUiDestination.route -> ""
                    else -> "Movisio"
                }
                val context = LocalContext.current
                val activity = context as? Activity
                var showExitDialog by remember { mutableStateOf(false) }


                BackHandler {

                    when (currentRoute) {
                        MovieHomeUiDestination.route -> showExitDialog=true
                        MovieFavUiDestination.route -> showExitDialog=true
                        UserUiDestination.route -> showExitDialog=true
                        AccountUiDestination.route -> showExitDialog=true
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(text = title) }
                        )
                    },
                    bottomBar = {
                        NavigationBar {

                            NavigationBarItem(
                                selected = currentRoute == MovieHomeUiDestination.route,
                                onClick = { navController.navigate(MovieHomeUiDestination.route) },
                                icon = {
                                    Icon(imageVector = Icons.Default.Home, contentDescription = "")
                                },
                                label = { Text(text = "Home") })
                            NavigationBarItem(
                                selected = currentRoute == MovieFavUiDestination.route,
                                onClick = { navController.navigate(MovieFavUiDestination.route) },
                                icon = {
                                    Icon(imageVector = Icons.Default.Star, contentDescription = "")
                                },
                                label = { Text(text = "Favorite") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == UserUiDestination.route,
                                onClick = { navController.navigate(UserUiDestination.route) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = ""
                                    )
                                },
                                label = { Text(text = "User") }
                            )
                        }
                    }

                ) { innerPadding ->
                    Hawk.init(context).build()
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                if (showExitDialog) {
                    ExitDialog(
                        onDismissRequest = { showExitDialog = false },
                        onConfirmation = {
                            showExitDialog = false
                            activity?.finishAffinity()
                        }
                    )
                }
            }
        }
    }
}

