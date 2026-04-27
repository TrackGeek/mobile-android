package net.trackgeek.mobile.android.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme

sealed class NavItem(val title: String, val icon: ImageVector, val route: String) {
    object Home : NavItem("Home", Icons.Default.Home, "home")
    object Search : NavItem("Search", Icons.Default.Search, "search")
    object List : NavItem("List", Icons.AutoMirrored.Filled.List, "list")
    object Profile : NavItem("Profile", Icons.Default.Person, "profile")
}

@Composable
fun BottomBar(
    currentRoute: String? = null,
    onNavigateToRoute: (String) -> Unit = {}
) {
    val items = listOf(
        NavItem.Home,
        NavItem.Search,
        NavItem.List,
        NavItem.Profile
    )

    NavigationBar(
        modifier = androidx.compose.ui.Modifier.height(100.dp),
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = { onNavigateToRoute(item.route) },
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    indicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    selectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    unselectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    TrackGeekTheme {
        BottomBar()
    }
}
