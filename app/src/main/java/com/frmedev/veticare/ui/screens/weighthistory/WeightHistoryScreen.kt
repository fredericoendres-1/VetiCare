package com.frmedev.veticare.ui.screens.weighthistory

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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

// --- UI State ---
data class WeightItemUiState(
    val id: String,
    val weight: String, // e.g., "55 kg"
    val date: String    // e.g., "5555-05-06"
)

// --- Main Screen ---
@Composable
fun WeightHistoryScreen(
    weightItems: List<WeightItemUiState>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onEditItemClick: (String) -> Unit,
    onDeleteItemClick: (String) -> Unit
) {
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
                text = stringResource(id = R.string.weight_history_title),
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

        // --- List ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = weightItems, key = { it.id }) { item ->
                WeightItemCard(
                    item = item,
                    onEditClick = { onEditItemClick(item.id) },
                    onDeleteClick = { onDeleteItemClick(item.id) }
                )
            }
        }
    }
}

@Composable
fun WeightItemCard(
    item: WeightItemUiState,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(VetiCardSurface),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⚖️", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.weight,
                    style = MaterialTheme.typography.titleMedium,
                    color = VetiTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = VetiTextSecondary,
                    fontSize = 13.sp
                )
            }

            // 3 Dots Menu Button
            Box {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.size(48.dp)
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

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun WeightHistoryScreenPreview() {
    VetiCareTheme {
        WeightHistoryScreen(
            weightItems = listOf(
                WeightItemUiState(
                    id = "1",
                    weight = "55 kg",
                    date = "5555-05-06"
                ),
                WeightItemUiState(
                    id = "2",
                    weight = "54 kg",
                    date = "2024-01-10"
                )
            ),
            onBackClick = {},
            onAddClick = {},
            onEditItemClick = {},
            onDeleteItemClick = {}
        )
    }
}