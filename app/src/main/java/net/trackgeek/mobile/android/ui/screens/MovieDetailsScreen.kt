package net.trackgeek.mobile.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import net.trackgeek.mobile.android.ui.icons.LucideProjector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: String,
    onBackClick: () -> Unit = {}
) {
    val movie = remember { getMockMovieDetails(movieId) }
    val scope = rememberCoroutineScope()
    var showStatusModal by remember { mutableStateOf(false) }
    var showProgressModal by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    val statuses = listOf("Planning", "Watching", "Completed", "Dropped", "Paused")
    var currentStatus by remember { mutableStateOf("Add to List") }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Spacer(modifier = Modifier.height(0.dp))
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding())
                    .verticalScroll(rememberScrollState())
            ) {

                Box(modifier = Modifier.height(420.dp)) {
                    AsyncImage(
                        model = movie.backdropUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.3f),
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    ),
                                    endY = 600f
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        FloatingActionButton(
                            onClick = onBackClick,
                            containerColor = Color.Black.copy(alpha = 0.5f),
                            contentColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp),
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            FloatingActionButton(
                                onClick = { isFavorite = !isFavorite },
                                containerColor = Color.Black.copy(alpha = 0.5f),
                                contentColor = if (isFavorite) Color.Red else Color.White,
                                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                                shape = CircleShape
                            ) {
                                Icon(
                                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite"
                                )
                            }
                            FloatingActionButton(
                                onClick = { /* Share */ },
                                containerColor = Color.Black.copy(alpha = 0.5f),
                                contentColor = Color.White,
                                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                                shape = CircleShape
                            ) {
                                Icon(Icons.Default.Share, contentDescription = "Share")
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 24.dp, end = 16.dp, bottom = 96.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        AsyncImage(
                            model = movie.posterUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp)
                                .shadow(16.dp, shape = RoundedCornerShape(16.dp))
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.padding(bottom = 8.dp)) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${movie.releaseDate} • ${movie.duration}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .background(Color(0xFFF5C518), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${movie.rating}/10",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { showStatusModal = true },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(currentStatus, fontWeight = FontWeight.SemiBold)
                    }
                    OutlinedButton(
                        onClick = { showProgressModal = true },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.outline)))
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Progress", fontWeight = FontWeight.SemiBold)
                    }
                }

                movie.collection?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(LucideProjector, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(it, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(movie.genres) { genre ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text(genre, fontWeight = FontWeight.Medium) },
                            shape = RoundedCornerShape(20.dp),
                            border = null,
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                }

                Text(
                    text = movie.synopsis,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 6. Info Grid Moderno
                SectionTitle("Details")
                InfoGrid(movie)

                // 7. Trailer
                SectionTitle("Trailer")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = movie.backdropUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().alpha(0.4f),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.White.copy(alpha = 0.9f), CircleShape)
                            .padding(end = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            modifier = Modifier.size(36.dp),
                            tint = Color.Black
                        )
                    }
                }

                SectionTitle("Links & Social")
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ModernSocialIcon(Icons.Default.Language, "Website")
                    ModernSocialIcon(Icons.Default.Link, "IMDB")
                    ModernSocialIcon(Icons.Default.Face, "Facebook")
                    ModernSocialIcon(Icons.Default.CameraAlt, "Instagram")
                }

                var selectedTabIndex by remember { mutableIntStateOf(0) }
                val tabTitles = listOf("Cast", "Reviews", "Lists", "Backdrops")

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (selectedTabIndex == index) MaterialTheme.colorScheme.primary
                                    else Color.Transparent
                                )
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTabIndex == index) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.heightIn(min = 200.dp)) {
                    when (selectedTabIndex) {
                        0 -> CastList(movie.cast)
                        1 -> ReviewsList(movie.reviews)
                        2 -> UserLists(movie.userLists)
                        3 -> BackdropsGrid(movie.backdrops)
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }

    if (showStatusModal) {
        ModalBottomSheet(
            onDismissRequest = { showStatusModal = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        ) {
            Text(
                "Select Status",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            statuses.forEach { status ->
                ListItem(
                    headlineContent = { Text(status, fontWeight = if (currentStatus == status) FontWeight.Bold else FontWeight.Normal) },
                    leadingContent = {
                        Icon(
                            if (currentStatus == status) Icons.Default.CheckCircle else Icons.Default.Circle,
                            contentDescription = null,
                            tint = if (currentStatus == status) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            currentStatus = status
                            showStatusModal = false
                        }
                        .padding(horizontal = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Bottom Sheet para Progresso
    if (showProgressModal) {
        ModalBottomSheet(
            onDismissRequest = { showProgressModal = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth()) {
                Text("Update Progress", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                Text("Progress: 100%", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = 1f,
                    onValueChange = {},
                    colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary, activeTrackColor = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showProgressModal = false },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save Progress", fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    )
}

@Composable
fun ModernSocialIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun InfoGrid(movie: MovieDetails) {
    // Usando 2 colunas simples com Row/Column para evitar complexidade excessiva de LazyGrid desnecessária aqui
    Column(modifier = Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            InfoCard(modifier = Modifier.weight(1f), label = "Director", value = movie.directors.joinToString(", "))
            InfoCard(modifier = Modifier.weight(1f), label = "Language", value = movie.language)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            InfoCard(modifier = Modifier.weight(1f), label = "Budget", value = movie.budget)
            InfoCard(modifier = Modifier.weight(1f), label = "Revenue", value = movie.revenue)
        }
        InfoCard(modifier = Modifier.fillMaxWidth(), label = "Production", value = movie.productionCompanies.joinToString(", "))
    }
}

@Composable
fun InfoCard(modifier: Modifier = Modifier, label: String, value: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun CastList(cast: List<CastMember>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cast) { member ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(80.dp)) {
                AsyncImage(
                    model = member.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(member.name, style = MaterialTheme.typography.bodySmall, maxLines = 2, textAlign = TextAlign.Center, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun ReviewsList(reviews: List<Review>) {
    Column(modifier = Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        reviews.forEach { review ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(review.author, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(review.content, style = MaterialTheme.typography.bodySmall, maxLines = 3, overflow = TextOverflow.Ellipsis, lineHeight = 18.sp)
                }
            }
        }
    }
}

@Composable
fun UserLists(lists: List<String>) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        lists.forEach { listName ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                ListItem(
                    headlineContent = { Text(listName, fontWeight = FontWeight.Medium) },
                    leadingContent = { Icon(Icons.Default.List, null, tint = MaterialTheme.colorScheme.primary) },
                    trailingContent = { Icon(Icons.Default.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
            }
        }
    }
}

@Composable
fun BackdropsGrid(backdrops: List<String>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(backdrops) { url ->
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .width(240.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
        }
    }
}

data class MovieDetails(
    val id: String, val title: String, val posterUrl: String, val backdropUrl: String,
    val synopsis: String, val rating: Double, val releaseDate: String, val status: String,
    val duration: String, val genres: List<String>, val directors: List<String>,
    val budget: String, val revenue: String, val language: String,
    val productionCompanies: List<String>, val collection: String? = null,
    val cast: List<CastMember> = emptyList(), val reviews: List<Review> = emptyList(),
    val userLists: List<String> = emptyList(), val backdrops: List<String> = emptyList()
)

data class CastMember(val name: String, val imageUrl: String)
data class Review(val author: String, val content: String)

private fun getMockMovieDetails(id: String) = MovieDetails(
    id = id, title = "Inception",
    posterUrl = "https://image.tmdb.org/t/p/w500/9e3Dz7aCANy5aRUQF745IlNloJ1.jpg",
    backdropUrl = "https://image.tmdb.org/t/p/original/8ZTVqvKDQ8emSGUEMjsS4yHAwrp.jpg",
    synopsis = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",
    rating = 8.8, releaseDate = "2010-07-15", status = "Released", duration = "2h 28m",
    genres = listOf("Action", "Sci-Fi", "Adventure"),
    directors = listOf("Christopher Nolan"), budget = "$160,000,000", revenue = "$828,322,032",
    language = "English", productionCompanies = listOf("Warner Bros. Pictures", "Legendary Pictures", "Syncopy"),
    collection = "Nolan's Mind-Bending Collection",
    cast = listOf(
        CastMember("Leonardo DiCaprio", "https://image.tmdb.org/t/p/w200/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg"),
        CastMember("Joseph Gordon-Levitt", "https://image.tmdb.org/t/p/w200/z2FA8js799xqtfiFjBTicFYdfk.jpg"),
        CastMember("Ellen Page", "https://image.tmdb.org/t/p/w200/nXO8DE4biVXY4UDYP0NdIY1zvXS.jpg")
    ),
    reviews = listOf(
        Review("John Doe", "A masterpiece of modern cinema. The visuals are stunning and the plot is incredibly well crafted."),
        Review("Jane Smith", "Incredible visuals and a complex story that keeps you guessing until the very end.")
    ),
    userLists = listOf("Top Sci-Fi Movies", "Best of Nolan", "Mind Bending Movies"),
    backdrops = listOf(
        "https://image.tmdb.org/t/p/original/ii8QGacT3MXESqBckQlyrATY0lT.jpg",
        "https://image.tmdb.org/t/p/original/28kKbSUvUz6P5RE1AuMJMO7IMfK.jpg"
    )
)