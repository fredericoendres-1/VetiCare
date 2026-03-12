package com.frmedev.veticare.ui.screens.healthtimeline

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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

enum class TimelineEventType {
    MEDICATION, WEIGHT, VACCINE, VET_VISIT, SYMPTOM
}

data class PetHeaderUiState(
    val name: String,
    val breed: String,
    val age: String,
    val weight: String,
    val avatarUrl: String? = null
)

data class TimelineItemUiState(
    val id: String,
    val eventType: TimelineEventType,
    val dateLabel: String,
    val title: String,
    val subtitle: String
)

// --- Main Screen ---

@Composable
fun HealthTimelineScreen(
    petHeader: PetHeaderUiState,
    timelineItems: List<TimelineItemUiState>,
    onBackClick: () -> Unit,
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
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.health_timeline_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = VetiTextPrimary
            )
        }

        // --- Timeline List ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp),
        ) {
            // Pet Header Card
            item {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(VetiBackground)
                                .border(2.dp, VetiOlive, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = VetiOlive
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = petHeader.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VetiTextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${petHeader.breed} • ${petHeader.age} • ${petHeader.weight}",
                                fontSize = 14.sp,
                                color = VetiTextSecondary
                            )
                        }
                    }
                }
            }

            // Timeline Items
            itemsIndexed(items = timelineItems, key = { _, item -> item.id }) { index, item ->
                TimelineRow(
                    item = item,
                    isFirst = index == 0,
                    isLast = index == timelineItems.size - 1,
                    onEditClick = { onEditItemClick(item.id) },
                    onDeleteClick = { onDeleteItemClick(item.id) }
                )
            }
        }
    }
}

@Composable
fun TimelineRow(
    item: TimelineItemUiState,
    isFirst: Boolean,
    isLast: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Height Intrinsic garante que a coluna da esquerda com a linha cresça
            // exatamente o tamanho do card de texto da direita.
            .height(IntrinsicSize.Min) 
    ) {
        // --- Left Column: Line and Icon ---
        Box(
            modifier = Modifier
                .width(56.dp)
                .fillMaxHeight()
        ) {
            // A linha vertical conectora
            val lineColor = VetiOutline.copy(alpha = 0.5f)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val iconCenterY = 32.dp.toPx()
                val startY = if (isFirst) iconCenterY else 0f
                val endY = if (isLast) iconCenterY else size.height
                val lineX = size.width / 2f

                drawLine(
                    color = lineColor,
                    start = Offset(lineX, startY),
                    end = Offset(lineX, endY),
                    strokeWidth = 1.5.dp.toPx()
                )
            }

            // The icon inside a circle
            val (iconText, bgColor) = getIconAndColorForType(item.eventType)
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(bgColor)
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iconText, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // --- Right Column: Card ---
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.dateLabel,
                        fontSize = 13.sp,
                        color = VetiTextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VetiTextPrimary
                    )
                    if (item.subtitle.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.subtitle,
                            fontSize = 14.sp,
                            color = VetiTextSecondary
                        )
                    }
                }

                // 3 Dots Menu Button
                Box {
                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = 8.dp, y = (-4).dp) // Pequeno ajuste para alinhar com o topo do texto
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

// Helper para definir os estilos dinâmicos de acordo com o enum
@Composable
private fun getIconAndColorForType(type: TimelineEventType): Pair<String, Color> {
    return when (type) {
        TimelineEventType.MEDICATION -> Pair("💊", VetiCardSurface)
        TimelineEventType.WEIGHT -> Pair("⚖️", VetiCardSurface)
        TimelineEventType.VACCINE -> Pair("💉", VetiCardSurface)
        TimelineEventType.VET_VISIT -> Pair("📅", VetiCardSurface)
        TimelineEventType.SYMPTOM -> Pair("🩺", VetiCardSurface)
    }
}

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun HealthTimelineScreenPreview() {
    VetiCareTheme {
        HealthTimelineScreen(
            petHeader = PetHeaderUiState(
                name = "fre",
                breed = "fr",
                age = "-54345 months",
                weight = "56kg"
            ),
            timelineItems = listOf(
                TimelineItemUiState(
                    id = "1",
                    eventType = TimelineEventType.MEDICATION,
                    dateLabel = "Invalid Date",
                    title = "Medication started: ssf",
                    subtitle = "546456, Custom"
                ),
                TimelineItemUiState(
                    id = "2",
                    eventType = TimelineEventType.WEIGHT,
                    dateLabel = "May 5",
                    title = "Weight recorded: 55kg",
                    subtitle = ""
                ),
                TimelineItemUiState(
                    id = "3",
                    eventType = TimelineEventType.VACCINE,
                    dateLabel = "May 5",
                    title = "Vaccine: Astrazaneca",
                    subtitle = "Next due: 2100-02-21"
                ),
                TimelineItemUiState(
                    id = "4",
                    eventType = TimelineEventType.VET_VISIT,
                    dateLabel = "Dec 21",
                    title = "Vet Visit: Medico bexiga",
                    subtitle = "Dr. Andre"
                )
            ),
            onBackClick = {},
            onEditItemClick = {},
            onDeleteItemClick = {}
        )
    }
}