package net.trackgeek.mobile.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.trackgeek.mobile.android.ui.components.CategoryTabs
import net.trackgeek.mobile.android.ui.icons.LucideBook
import net.trackgeek.mobile.android.ui.icons.LucideBookOpenText
import net.trackgeek.mobile.android.ui.icons.LucideGamepad2
import net.trackgeek.mobile.android.ui.icons.LucideListFilter
import net.trackgeek.mobile.android.ui.icons.LucideMountain
import net.trackgeek.mobile.android.ui.icons.LucideProjector
import net.trackgeek.mobile.android.ui.icons.LucideTvMinimalPlay
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen() {
    var selectedCategory by remember { mutableStateOf("Movies") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("Movies", "TV Shows", "Games", "Mangas", "Books", "Animes")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Planning", "Reading", "Completed", "Reread", "Paused", "Dropped")

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
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryTabs(
                    tabs = tabs,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { /* TODO: Adicionar ação de filtro */ },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = LucideListFilter,
                        contentDescription = "Filtro",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Conteúdo de ${tabs[selectedTabIndex]}",
                    modifier = Modifier.padding(16.dp)
                )
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
        else -> Icons.Default.Search
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    TrackGeekTheme {
        ListScreen()
    }
}
