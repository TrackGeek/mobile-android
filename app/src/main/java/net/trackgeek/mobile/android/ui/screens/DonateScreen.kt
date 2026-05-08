package net.trackgeek.mobile.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DonateScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Donate") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Support TrackGeek",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Help us keep the project alive and ad-free! Every contribution helps us maintain and improve the platform for everyone.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Main Donate Action Card
            item {
                DonateActionCard()
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

            // Perks Section
            item {
                Text(
                    text = "Donation Perks",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(getMockPerks()) { perk ->
                PerkCard(perk)
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

            // Contributors Section
            item {
                Text(
                    text = "Our Geeks",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 4
                ) {
                    getMockContributors().forEach { contributor ->
                        ContributorItem(contributor)
                    }
                }
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

            // Transparency Reports
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Transparency Reports",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { /* Handle external link: https://drive.proton.me/urls/E1WHSDDQ0M#0zZ3zOelpK8q */ },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("View All")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DonateActionCard() {
    var showDonateDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Wants to donate?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Choose a donation type and amount to help us grow.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { showDonateDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Coffee, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Donate")
            }
        }
    }

    if (showDonateDialog) {
        DonateDialog(onDismiss = { showDonateDialog = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateDialog(onDismiss: () -> Unit) {
    var donationType by remember { mutableStateOf("OneTime") } // OneTime, Monthly
    var amountType by remember { mutableStateOf("fixed") } // fixed, custom
    var selectedAmount by remember { mutableStateOf(5) }
    var customAmount by remember { mutableStateOf("") }

    val fixedAmounts = listOf(2, 5, 10, 25)
    val finalAmount = if (amountType == "fixed") selectedAmount.toString() else if (customAmount.isEmpty()) "0" else customAmount

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Donate", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        "Select your preferred donation method.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Donation Type (OneTime / Monthly)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DonationToggleButton(
                        text = "One-time",
                        isSelected = donationType == "OneTime",
                        onClick = { donationType = "OneTime" },
                        modifier = Modifier.weight(1f)
                    )
                    DonationToggleButton(
                        text = "Monthly",
                        isSelected = donationType == "Monthly",
                        onClick = { donationType = "Monthly" },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Amount Type (Fixed / Custom)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DonationToggleButton(
                        text = "Fixed",
                        isSelected = amountType == "fixed",
                        onClick = { amountType = "fixed" },
                        modifier = Modifier.weight(1f)
                    )
                    DonationToggleButton(
                        text = "Custom",
                        isSelected = amountType == "custom",
                        onClick = { amountType = "custom" },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (amountType == "fixed") {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        fixedAmounts.forEach { amount ->
                            OutlinedButton(
                                onClick = { selectedAmount = amount },
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = if (selectedAmount == amount) {
                                    ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                } else ButtonDefaults.outlinedButtonColors(),
                                border = if (selectedAmount == amount) null else ButtonDefaults.outlinedButtonBorder
                            ) {
                                Text("€$amount", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = customAmount,
                        onValueChange = { if (it.length <= 6) customAmount = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Enter amount") },
                        prefix = { Text("€ ") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "€ $finalAmount",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = { /* Handle Payment Integration */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = finalAmount.toDoubleOrNull()?.let { it > 0 } ?: false
                ) {
                    Text("Donate € $finalAmount", style = MaterialTheme.typography.titleMedium)
                }

                Text(
                    "Your contribution helps us keep the project alive. Thank you!",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DonationToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(12.dp),
        colors = if (isSelected) {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun PerkCard(perk: Perk) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = perk.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "€ ${perk.value}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = perk.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                perk.benefits.forEach { benefit ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = benefit,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContributorItem(contributor: Contributor) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = contributor.name,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontWeight = FontWeight.Medium
        )
    }
}

data class Perk(
    val name: String,
    val value: String,
    val description: String,
    val benefits: List<String>
)

data class Contributor(
    val name: String,
    val avatarUrl: String
)

fun getMockPerks() = listOf(
    Perk("Bronze Geek", "5.00", "Support the project basics.", listOf("Special Discord Role", "Exclusive Profile Badge")),
    Perk("Silver Geek", "10.00", "Extra support for new features.", listOf("Silver Discord Role", "Silver Profile Badge", "Early Access to Beta")),
    Perk("Gold Geek", "25.00", "The ultimate supporter tier.", listOf("Gold Discord Role", "Gold Profile Badge", "Direct Input on Roadmap", "Special Thanks in App"))
)

fun getMockContributors() = listOf(
    Contributor("Avent", ""),
    Contributor("TrackGeek", ""),
    Contributor("GeekFan", ""),
    Contributor("Supporter1", ""),
    Contributor("Donor99", ""),
    Contributor("MVP", "")
)

@Preview(showBackground = true)
@Composable
fun DonateScreenPreview() {
    TrackGeekTheme {
        DonateScreen(onBackClick = {})
    }
}
