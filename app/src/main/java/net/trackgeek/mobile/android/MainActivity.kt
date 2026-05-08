package net.trackgeek.mobile.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.trackgeek.mobile.android.ui.components.BottomBar
import net.trackgeek.mobile.android.ui.components.MediaType
import net.trackgeek.mobile.android.ui.screens.DonateScreen
import net.trackgeek.mobile.android.ui.screens.HomeScreen
import net.trackgeek.mobile.android.ui.screens.ListScreen
import net.trackgeek.mobile.android.ui.screens.MovieDetailsScreen
import net.trackgeek.mobile.android.ui.screens.ProfileScreen
import net.trackgeek.mobile.android.ui.screens.SearchScreen
import net.trackgeek.mobile.android.ui.screens.SettingsScreen
import net.trackgeek.mobile.android.ui.screens.SplashScreen
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())

        setContent {
            TrackGeekTheme {
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route

                val showBottomBar = currentRoute in listOf("home", "search", "list", "profile")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(
                                currentRoute = currentRoute,
                                onNavigateToRoute = { route ->
                                    navController.navigate(route) {
                                        navController.graph.startDestinationRoute?.let { startRoute ->
                                            popUpTo(startRoute) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("splash") {
                            SplashScreen(
                                onAnimationFinished = {
                                    navController.navigate("home") {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                onMediaClick = { media ->
                                    if (media.type == MediaType.Movie) {
                                        navController.navigate("movie_details/${media.id}")
                                    }
                                }
                            )
                        }
                        composable("search") {
                            Box(modifier = Modifier.padding(innerPadding)) {
                                SearchScreen()
                            }
                        }
                        composable("list") {
                            Box(modifier = Modifier.padding(innerPadding)) {
                                ListScreen()
                            }
                        }
                        composable("profile") {
                            Box(modifier = Modifier.padding(innerPadding)) {
                                ProfileScreen(
                                    isOwnProfile = true,
                                    onSettingsClick = { navController.navigate("settings") }
                                )
                            }
                        }
                        composable("settings") {
                            Box(modifier = Modifier.padding(innerPadding)) {
                                SettingsScreen(
                                    onBackClick = { navController.popBackStack() },
                                    onDonateClick = { navController.navigate("donate") }
                                )
                            }
                        }
                        composable("donate") {
                            Box(modifier = Modifier.padding(innerPadding)) {
                                DonateScreen(
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                        composable("movie_details/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            if (movieId != null) {
                                Box(modifier = Modifier.padding(innerPadding)) {
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
}
