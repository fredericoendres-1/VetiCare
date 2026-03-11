package com.frmedev.veticare.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.components.DashboardGridCard
import com.frmedev.veticare.ui.components.QuickActionItem
import com.frmedev.veticare.ui.theme.*

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onPetSelected: (String) -> Unit,
    onAddPetClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCardClick: (String) -> Unit,
    onQuickActionClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .padding(horizontal = 24.dp)
            .padding(top = 48.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Top Bar (Custom) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Seletor de Pets
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiState.pets.forEach { pet ->
                    val isSelected = pet.id == uiState.selectedPetId
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 48.dp else 40.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray) // Placeholder da foto do pet
                            .border(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) VetiOlive else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { onPetSelected(pet.id) },
                        contentAlignment = Alignment.Center
                    ) {
                        // Ícone temporário (substituiremos por AsyncImage do Coil depois)
                        Icon(Icons.Default.Face, contentDescription = pet.name, tint = VetiTextSecondary)
                    }
                }

                // Botão de Adicionar Pet
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, VetiOutline, CircleShape)
                        .clip(CircleShape)
                        .clickable { onAddPetClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Pet", tint = VetiTextSecondary)
                }
            }

            Text(
                text = stringResource(id = R.string.home_app_title),
                color = VetiTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold // Ideal seria FontFamily.Serif aqui
            )

            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = VetiTextPrimary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Next Action Card ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(VetiPeach) // Cor de destaque quente
                .padding(20.dp)
                .clickable { onCardClick("NextAction") }
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.home_next_action_label),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Ícone Pílula (Placeholder)
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = uiState.nextAction ?: stringResource(id = R.string.home_next_action_empty),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Bento Box Grid (2x2) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardGridCard(
                title = stringResource(id = R.string.home_card_vaccine),
                subtitle = uiState.nextVaccineDate ?: stringResource(id = R.string.home_card_none_scheduled),
                icon = Icons.Default.Warning, // Syringe placeholder
                onClick = { onCardClick("Vaccine") },
                modifier = Modifier.weight(1f)
            )
            DashboardGridCard(
                title = stringResource(id = R.string.home_card_vet_visit),
                subtitle = uiState.nextVetVisitDate ?: stringResource(id = R.string.home_card_none_scheduled),
                icon = Icons.Default.DateRange, // Calendar placeholder
                onClick = { onCardClick("VetVisit") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardGridCard(
                title = stringResource(id = R.string.home_card_health_records),
                subtitle = null,
                icon = Icons.Default.List, // Stethoscope placeholder
                backgroundColor = Color(0xFFEFECE5), // Fundo levemente bege para quebrar o padrão
                onClick = { onCardClick("HealthRecords") },
                modifier = Modifier.weight(1f)
            )
            DashboardGridCard(
                title = stringResource(id = R.string.home_card_pet_profile, uiState.selectedPetName),
                subtitle = null,
                icon = Icons.Default.Face, // Dog placeholder
                onClick = { onCardClick("PetProfile") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Quick Actions ---
        Text(
            text = stringResource(id = R.string.home_quick_actions_title),
            color = VetiTextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickActionItem(
                label = stringResource(id = R.string.home_quick_action_weight),
                icon = Icons.Default.Info, // Placeholder
                onClick = { onQuickActionClick("Weight") }
            )
            QuickActionItem(
                label = stringResource(id = R.string.home_quick_action_add_med),
                icon = Icons.Default.AddCircle, // Placeholder
                onClick = { onQuickActionClick("AddMed") }
            )
            QuickActionItem(
                label = stringResource(id = R.string.home_quick_action_history),
                icon = Icons.Default.List, // Placeholder
                onClick = { onQuickActionClick("History") }
            )
            QuickActionItem(
                label = stringResource(id = R.string.home_quick_action_vet_visit),
                icon = Icons.Default.DateRange, // Placeholder
                onClick = { onQuickActionClick("VetVisit") }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Ad Space ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(VetiCardSurface, RoundedCornerShape(30.dp))
                .border(1.dp, VetiOutline, RoundedCornerShape(30.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.home_ad_space_placeholder),
                color = VetiTextSecondary,
                fontSize = 14.sp
            )
        }
    }
}

// --- Previews ---

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    VetiCareTheme {
        HomeScreen(
            uiState = HomeUiState(
                pets = listOf(
                    PetMini(id = "1", name = "Fred"),
                    PetMini(id = "2", name = "Max")
                ),
                selectedPetId = "1"
            ),
            onPetSelected = {},
            onAddPetClick = {},
            onSettingsClick = {},
            onCardClick = {},
            onQuickActionClick = {}
        )
    }
}