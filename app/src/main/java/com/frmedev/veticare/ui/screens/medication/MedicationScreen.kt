package com.frmedev.veticare.ui.screens.medication

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.theme.*

// --- UI State & Enums ---
enum class MedicationFilter {
    ALL, ACTIVE, INACTIVE
}

data class MedicationItemUiState(
    val id: String,
    val name: String,
    val info: String, // e.g., "Custom", "250mg • Twice a day"
    val isActive: Boolean
)

// --- Main Screen ---
@Composable
fun MedicationScreen(
    medications: List<MedicationItemUiState>,
    currentFilter: MedicationFilter,
    onFilterChange: (MedicationFilter) -> Unit,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onMarkTakenClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    // Dynamic Title based on filter
    val titleRes = when (currentFilter) {
        MedicationFilter.ALL -> R.string.medications_title_all
        MedicationFilter.ACTIVE -> R.string.medications_title_active
        MedicationFilter.INACTIVE -> R.string.medications_title_inactive
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding()
    ) {
        // --- Top Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back Button
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_back_button),
                    tint = VetiTextPrimary
                )
            }

            // Title
            Text(
                text = stringResource(id = titleRes),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = VetiTextPrimary,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Add Button
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(VetiOlive)
                    .clickable { onAddClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.cd_add_button),
                    tint = Color.White
                )
            }
        }

        // --- Filter Chips ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp), // Corrigido erro de "horizontal + bottom"
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterChipItem(
                label = stringResource(id = R.string.medications_filter_active),
                isSelected = currentFilter == MedicationFilter.ACTIVE,
                onClick = { onFilterChange(MedicationFilter.ACTIVE) }
            )
            FilterChipItem(
                label = stringResource(id = R.string.medications_filter_inactive),
                isSelected = currentFilter == MedicationFilter.INACTIVE,
                onClick = { onFilterChange(MedicationFilter.INACTIVE) }
            )
            FilterChipItem(
                label = stringResource(id = R.string.medications_filter_all),
                isSelected = currentFilter == MedicationFilter.ALL,
                onClick = { onFilterChange(MedicationFilter.ALL) }
            )
        }

        // --- List ---
        val filteredList = medications.filter {
            when (currentFilter) {
                MedicationFilter.ALL -> true
                MedicationFilter.ACTIVE -> it.isActive
                MedicationFilter.INACTIVE -> !it.isActive
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp), // Corrigido erro de "horizontal + bottom"
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = filteredList, key = { it.id }) { med ->
                MedicationItemCard(
                    medication = med,
                    onMarkTakenClick = { onMarkTakenClick(med.id) },
                    onEditClick = { onEditClick(med.id) },
                    onDeleteClick = { onDeleteClick(med.id) }
                )
            }
        }
    }
}

@Composable
private fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) VetiOlive else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isSelected) VetiOlive else VetiOutline,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else VetiTextSecondary,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
fun MedicationItemCard(
    medication: MedicationItemUiState,
    onMarkTakenClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Pill Placeholder
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(VetiBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "💊", fontSize = 24.sp) // Emoji no lugar de ícone por enquanto
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medication.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = VetiTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = medication.info,
                    style = MaterialTheme.typography.bodyMedium,
                    color = VetiTextSecondary
                )
            }

            // Trailing Actions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Checkmark Button (Mark as taken)
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(VetiBackground)
                        .clickable { onMarkTakenClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.cd_mark_taken),
                        tint = VetiTextSecondary
                    )
                }

                // 3 Dots Menu Button (Edit/Delete)
                Box {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(VetiBackground) // Mesmo estilo do Checkmark button
                            .clickable { expanded = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.cd_more_options),
                            tint = VetiTextSecondary
                        )
                    }
                    
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.action_edit), color = VetiTextPrimary) },
                            onClick = {
                                expanded = false
                                onEditClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.action_delete), color = MaterialTheme.colorScheme.error) },
                            onClick = {
                                expanded = false
                                onDeleteClick()
                            }
                        )
                    }
                }
            }
        }
    }
}

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun MedicationScreenPreview() {
    VetiCareTheme {
        var filter by remember { mutableStateOf(MedicationFilter.ACTIVE) }
        
        MedicationScreen(
            medications = listOf(
                MedicationItemUiState(
                    id = "1",
                    name = "Amoxicillin",
                    info = "Custom",
                    isActive = true
                ),
                MedicationItemUiState(
                    id = "2",
                    name = "NexGard",
                    info = "Monthly",
                    isActive = true
                ),
                MedicationItemUiState(
                    id = "3",
                    name = "Old Med",
                    info = "Stopped 2023",
                    isActive = false
                )
            ),
            currentFilter = filter,
            onFilterChange = { filter = it },
            onBackClick = {},
            onAddClick = {},
            onMarkTakenClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}