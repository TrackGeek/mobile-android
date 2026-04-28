package net.trackgeek.mobile.android.ui.screens

import net.trackgeek.mobile.android.ui.icons.LucideGamepad2
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.trackgeek.mobile.android.ui.icons.LucideBook
import net.trackgeek.mobile.android.ui.icons.LucideBookOpenText
import net.trackgeek.mobile.android.ui.icons.LucideListFilter
import net.trackgeek.mobile.android.ui.icons.LucideMountain
import net.trackgeek.mobile.android.ui.icons.LucideProjector
import net.trackgeek.mobile.android.ui.icons.LucideTvMinimalPlay
import net.trackgeek.mobile.android.ui.icons.LucideUsers
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Movies") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("Movies", "TV Shows", "Games", "Mangas", "Books", "Animes", "Users")

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { expanded = true },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    Icon(
                        imageVector = getCategoryIcon(selectedCategory),
                        contentDescription = "Category: $selectedCategory"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    shape = MaterialTheme.shapes.extraLarge,
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    tonalElevation = 0.dp,
                    shadowElevation = 4.dp,
                ) {
                    categories.forEach { category ->
                        val isSelected = category == selectedCategory
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = getCategoryIcon(category),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            },
                            colors = MenuItemColors(
                                textColor = MaterialTheme.colorScheme.onSurface,
                                leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                            ),
                            modifier = if (isSelected)
                                Modifier.background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                            else
                                Modifier
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        "Search in $selectedCategory...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    )
                },
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                trailingIcon = {
                    IconButton(onClick = { /* TODO: Handle filter action */ }) {
                        Icon(
                            imageVector = LucideListFilter,
                            contentDescription = "Filter"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (searchQuery.isNotEmpty()) {
                // Resultados aqui
            } else {
                // Conteúdo vazio ou histórico
            }
        }
    }
}

private fun getCategoryIcon(category: String): ImageVector {
    return when (category) {
        "Movies" -> LucideProjector
        "TV Shows" -> LucideTvMinimalPlay
        "Games" -> LucideGamepad2
        "Mangas" -> LucideBookOpenText
        "Books" -> LucideBook
        "Animes" -> LucideMountain
        "Users" -> LucideUsers
        else -> Icons.Default.Search
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    TrackGeekTheme {
        SearchScreen()
    }
}
