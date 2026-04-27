package net.trackgeek.mobile.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.trackgeek.mobile.android.ui.components.CategoryTabs
import net.trackgeek.mobile.android.ui.components.MediaCarousel
import net.trackgeek.mobile.android.ui.components.MediaItem

@Composable
fun HomeScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Feed", "Movies", "TV Shows", "Games", "Mangas", "Books", "Animes")

    Column {
        CategoryTabs(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            if (tabs[selectedTabIndex] != "Feed") {
                val mockMedia = getMockMediaForCategory(tabs[selectedTabIndex])
                MediaCarousel(
                    items = mockMedia,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Text(
                text = "Conteúdo de ${tabs[selectedTabIndex]}",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

private fun getMockMediaForCategory(category: String): List<MediaItem> {
    return when (category) {
        "Movies" -> listOf(
            MediaItem("Inception", "https://image.tmdb.org/t/p/w500/edv5CZvnc0U9YvO679IqCgl7ndC.jpg"),
            MediaItem("The Dark Knight", "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDp9QmSJJIVzTVbvSJp.jpg"),
            MediaItem("Interstellar", "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg")
        )
        "TV Shows" -> listOf(
            MediaItem("Breaking Bad", "https://image.tmdb.org/t/p/w500/ggws9uIDss9A5Yq366f07Y2f8vN.jpg"),
            MediaItem("The Bear", "https://image.tmdb.org/t/p/w500/96XS9vP86T75X67g5fHlU6V7qL0.jpg"),
            MediaItem("Succession", "https://image.tmdb.org/t/p/w500/77S99Xp9BgS9uWj6vXpUvQf4S8.jpg")
        )
        "Games" -> listOf(
            MediaItem("Elden Ring", "https://media.rawg.io/media/games/511/5118bef50a3b535c18ed39095db1a961.jpg"),
            MediaItem("God of War", "https://media.rawg.io/media/games/4be/4be7a6ad3e35147514a6e3823485f47a.jpg"),
            MediaItem("The Last of Us", "https://media.rawg.io/media/games/d58/d588947d428671478960d2bc4d406535.jpg")
        )
        else -> listOf(
            MediaItem("Berserk", "https://picsum.photos/seed/berserk/500/300"),
            MediaItem("One Piece", "https://picsum.photos/seed/onepiece/500/300"),
            MediaItem("Tonikaku Kawaii", "https://picsum.photos/seed/tonikaku/500/300")
        )
    }
}
