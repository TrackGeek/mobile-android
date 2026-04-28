package net.trackgeek.mobile.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.trackgeek.mobile.android.ui.components.CategoryTabs
import net.trackgeek.mobile.android.ui.icons.LucideBook
import net.trackgeek.mobile.android.ui.icons.LucideBookOpenText
import net.trackgeek.mobile.android.ui.icons.LucideMountain
import net.trackgeek.mobile.android.ui.icons.LucideProjector
import net.trackgeek.mobile.android.ui.icons.LucideTvMinimalPlay
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme

data class ProfileBadge(
    val icon: ImageVector,
    val label: String,
    val color: Color
)

data class FavoriteItem(
    val title: String,
    val category: String,
    val rating: Float
)

data class FeedEntry(
    val action: String,
    val target: String,
    val timestamp: String,
    val icon: ImageVector
)

data class StatItem(
    val label: String,
    val value: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isOwnProfile: Boolean = false
) {
    var isFollowing by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Feed", "Favourites", "Stats", "Badges")

    val badges = listOf(
        ProfileBadge(Icons.Filled.Star, "Early Adopter", Color(0xFFFFC107)),
        ProfileBadge(Icons.Filled.Favorite, "Cinephile", Color(0xFFE91E63)),
        ProfileBadge(Icons.Filled.CheckCircle, "100 Reviews", Color(0xFF4CAF50)),
        ProfileBadge(Icons.Filled.Bolt, "Power User", Color(0xFF9C27B0)),
        ProfileBadge(Icons.Filled.EmojiEvents, "Top Reviewer", Color(0xFFFF5722)),
    )

    val favorites = listOf(
        FavoriteItem("Inception", "Movies", 5.0f),
        FavoriteItem("Attack on Titan", "Animes", 4.8f),
        FavoriteItem("Spider-Man", "Games", 5.0f),
        FavoriteItem("Spy x Family", "Animes", 4.7f),
        FavoriteItem("Dr. Stone", "Animes", 4.6f),
    )

    val feed = listOf(
        FeedEntry("Rated", "Inception", "2h ago", Icons.Outlined.Star),
        FeedEntry("Added to list", "The Witcher", "5h ago", Icons.Outlined.Add),
        FeedEntry("Reviewed", "Spider-Man 2", "1d ago", Icons.Outlined.RateReview),
        FeedEntry("Completed", "Dr. Stone", "2d ago", Icons.Outlined.CheckCircle),
        FeedEntry("Started watching", "Severance", "3d ago", Icons.Outlined.PlayArrow),
    )

    val stats = listOf(
        StatItem("Movies", "142", LucideProjector),
        StatItem("TV Shows", "38", LucideTvMinimalPlay),
        StatItem("Animes", "91", LucideMountain),
        StatItem("Games", "24", Icons.Filled.SportsEsports),
        StatItem("Books", "17", LucideBook),
        StatItem("Mangas", "53", LucideBookOpenText),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if (!isOwnProfile) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Share, contentDescription = "Share")
                    }
                    if (isOwnProfile) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Outlined.Settings, contentDescription = "Settings")
                        }
                    } else {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                ProfileHeader(
                    isOwnProfile = isOwnProfile,
                    isFollowing = isFollowing,
                    onFollowClick = { isFollowing = !isFollowing }
                )
            }

            item {
                CategoryTabs(
                    tabs = tabs,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it }
                )
            }

            when (selectedTabIndex) {
                0 -> {
                    items(feed) { entry ->
                        FeedEntryItem(entry = entry)
                    }
                }
                1 -> {
                    item {
                        FavouritesSection(favorites = favorites)
                    }
                }
                2 -> {
                    item {
                        StatsSection(stats = stats)
                    }
                }
                3 -> {
                    item {
                        BadgesSection(badges = badges)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    isOwnProfile: Boolean,
    isFollowing: Boolean,
    onFollowClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 88.dp)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isOwnProfile) {
                    OutlinedButton(
                        onClick = {},
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text("Edit Profile", style = MaterialTheme.typography.labelMedium)
                    }
                } else {
                    Button(
                        onClick = onFollowClick,
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFollowing)
                                MaterialTheme.colorScheme.surfaceVariant
                            else
                                MaterialTheme.colorScheme.primary,
                            contentColor = if (isFollowing)
                                MaterialTheme.colorScheme.onSurfaceVariant
                            else
                                MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Icon(
                            imageVector = if (isFollowing) Icons.Default.Check else Icons.Default.PersonAdd,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isFollowing) "Following" else "Follow",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Bruno Afonso",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "@brunoa",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Media tracker & developer. Building @TrackGeek. Spider-Man fan. 🕷️",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                FollowStat(label = "Followers", value = "1.2K")
                FollowStat(label = "Following", value = "348")
                FollowStat(label = "Reviews", value = "94")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FollowStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun FeedEntryItem(entry: FeedEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = entry.icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${entry.action} ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = entry.target,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = entry.timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
private fun FavouritesSection(favorites: List<FavoriteItem>) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        favorites.forEach { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = item.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFFFFC107)
                        )
                        Text(
                            text = item.rating.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsSection(stats: List<StatItem>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Tracked",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        stats.chunked(2).forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { stat ->
                    StatCard(
                        stat = stat,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun StatCard(stat: StatItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = stat.icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = stat.value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stat.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun BadgesSection(badges: List<ProfileBadge>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Badges",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        badges.chunked(3).forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                row.forEach { badge ->
                    BadgeItem(
                        badge = badge,
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(3 - row.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun BadgeItem(badge: ProfileBadge, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(badge.color.copy(alpha = 0.15f))
                .border(2.dp, badge.color.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = badge.icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = badge.color
            )
        }
        Text(
            text = badge.label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TrackGeekTheme {
        ProfileScreen(isOwnProfile = false)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenOwnPreview() {
    TrackGeekTheme {
        ProfileScreen(isOwnProfile = true)
    }
}