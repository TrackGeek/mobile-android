package net.trackgeek.mobile.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.trackgeek.mobile.android.ui.components.BottomBar
import net.trackgeek.mobile.android.ui.components.MediaType
import net.trackgeek.mobile.android.ui.screens.HomeScreen
import net.trackgeek.mobile.android.ui.screens.ListScreen
import net.trackgeek.mobile.android.ui.screens.MovieDetailsScreen
import net.trackgeek.mobile.android.ui.screens.ProfileScreen
import net.trackgeek.mobile.android.ui.screens.SearchScreen
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TrackGeekTheme {
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(
                            currentRoute = currentRoute,
                            onNavigateToRoute = { route ->
                                navController.navigate(route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    navController.graph.startDestinationRoute?.let { startRoute ->
                                        popUpTo(startRoute) {
                                            saveState = true
                                        }
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(onMediaClick = { media ->
                                if (media.type == MediaType.Movie) {
                                    navController.navigate("movie_details/${media.id}")
                                }
                            })
                        }
                        composable("search") {
                            SearchScreen()
                        }
                        composable("list") {
                            ListScreen()
                        }
                        composable("profile") {
                            ProfileScreen()
                        }
                        composable("movie_details/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            if (movieId != null) {
                                MovieDetailsScreen(
                                    movieId = movieId,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
