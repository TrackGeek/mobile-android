package net.trackgeek.mobile.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.trackgeek.mobile.android.ui.components.CategoryTabs
import net.trackgeek.mobile.android.ui.components.MediaCarousel
import net.trackgeek.mobile.android.ui.components.MediaItem
import net.trackgeek.mobile.android.ui.components.MediaSection

@Composable
fun HomeScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Feed", "Movies", "TV Shows", "Games", "Mangas", "Books", "Animes")
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        CategoryTabs(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (tabs[selectedTabIndex] != "Feed") {
                val featuredMedia = getMockMediaForCategory(tabs[selectedTabIndex])
                MediaCarousel(
                    items = featuredMedia,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )

                val sections = getMockSectionsForCategory(tabs[selectedTabIndex])
                sections.forEach { section ->
                    MediaSection(
                        title = section.title,
                        items = section.items,
                        onViewAllClick = { /* TODO */ },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            } else {
                Text(
                    text = "Feed Content",
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class HomeSection(
    val title: String,
    val items: List<MediaItem>
)

private fun getMockSectionsForCategory(category: String): List<HomeSection> {
    val type = when (category) {
        "Movies" -> "Movie"
        "TV Shows" -> "TV Show"
        "Games" -> "Game"
        "Mangas" -> "Manga"
        "Books" -> "Book"
        "Animes" -> "Anime"
        else -> ""
    }

    val sectionTitles = when (category) {
        "Movies" -> listOf("Now Playing", "Popular", "Top Rated", "Upcoming")
        "TV Shows" -> listOf("Airing Today", "On The Air", "Popular", "Top Rated")
        "Games" -> listOf("New Releases", "Popular", "Coming Soon")
        "Mangas" -> listOf("Latest Chapters", "Most Popular", "Trending", "New Mangas", "Completed")
        "Books" -> listOf("Bestsellers", "New Releases", "Award Winners", "Classic Books")
        "Animes" -> listOf("Seasonal (Winter 2024)", "Popular This Week", "Top Rated", "Classic Animes", "Movies", "Music")
        else -> listOf("Popular", "Trending")
    }

    return sectionTitles.map { title ->
        HomeSection(
            title = title,
            items = List(12) { i ->
                MediaItem("$title $type $i", "https://picsum.photos/seed/${category}_${title}_$i/200/300", type)
            }
        )
    }
}

private fun getMockMediaForCategory(category: String): List<MediaItem> {
    val type = when (category) {
        "Movies" -> "Movie"
        "TV Shows" -> "TV Show"
        "Games" -> "Game"
        "Mangas" -> "Manga"
        "Books" -> "Book"
        "Animes" -> "Anime"
        else -> ""
    }
    return when (category) {
        "Movies" -> listOf(
            MediaItem("Inception", "https://image.tmdb.org/t/p/w500/edv5CZvnc0U9YvO679IqCgl7ndC.jpg", type),
            MediaItem("The Dark Knight", "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDp9QmSJJIVzTVbvSJp.jpg", type),
            MediaItem("Interstellar", "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", type)
        )
        "TV Shows" -> listOf(
            MediaItem("Breaking Bad", "https://image.tmdb.org/t/p/w500/ggws9uIDss9A5Yq366f07Y2f8vN.jpg", type),
            MediaItem("The Bear", "https://image.tmdb.org/t/p/w500/96XS9vP86T75X67g5fHlU6V7qL0.jpg", type),
            MediaItem("Succession", "https://image.tmdb.org/t/p/w500/77S99Xp9BgS9uWj6vXpUvQf4S8.jpg", type)
        )
        "Games" -> listOf(
            MediaItem("Elden Ring", "https://media.rawg.io/media/games/511/5118bef50a3b535c18ed39095db1a961.jpg", type),
            MediaItem("God of War", "https://media.rawg.io/media/games/4be/4be7a6ad3e35147514a6e3823485f47a.jpg", type),
            MediaItem("The Last of Us", "https://media.rawg.io/media/games/d58/d588947d428671478960d2bc4d406535.jpg", type)
        )
        else -> listOf(
            MediaItem("Berserk", "https://picsum.photos/seed/berserk/500/300", type),
            MediaItem("One Piece", "https://picsum.photos/seed/onepiece/500/300", type),
            MediaItem("Tonikaku Kawaii", "https://picsum.photos/seed/tonikaku/500/300", type)
        )
    }
}
