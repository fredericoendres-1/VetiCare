package com.frmedev.veticare.ui.screens.mypets

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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
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
import com.frmedev.veticare.ui.theme.*

// Modelo de UI para a lista (Camada de UI apenas)
data class PetItemUiState(
    val id: String,
    val name: String,
    val breed: String,
    val age: Int,
    val weight: Double,
    val isSenior: Boolean = false,
    val avatarUrl: String? = null
)

@Composable
fun MyPetsScreen(
    pets: List<PetItemUiState>,
    onAddPetClick: () -> Unit,
    onPetClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding()
    ) {
        // --- Header Customizado (Conforme imagem) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.my_pets_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = VetiTextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(id = R.string.my_pets_count_format, pets.size),
                    style = MaterialTheme.typography.bodyMedium,
                    color = VetiTextSecondary
                )
            }

            // Botão de Adicionar (Estilo FAB no topo)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(VetiOlive)
                    .clickable { onAddPetClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.cd_add_button),
                    tint = Color.White
                )
            }
        }

        // --- Lista de Pets ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = pets, key = { it.id }) { pet ->
                PetListItem(
                    pet = pet,
                    onClick = { onPetClick(pet.id) }
                )
            }
        }
    }
}

@Composable
fun PetListItem(
    pet: PetItemUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        color = VetiCardSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Container do Ícone/Avatar
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VetiBackground)
                    .border(1.dp, VetiOutline.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = stringResource(id = R.string.cd_pet_icon),
                    modifier = Modifier.size(32.dp),
                    tint = VetiTextSecondary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = VetiTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    
                    if (pet.isSenior) {
                        Spacer(modifier = Modifier.width(8.dp))
                        SeniorBadge()
                    }
                }
                
                Text(
                    text = stringResource(
                        id = R.string.pet_subtitle_format,
                        pet.breed,
                        pet.age,
                        pet.weight
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = VetiTextSecondary
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.cd_pet_detail),
                tint = VetiTextSecondary.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun SeniorBadge() {
    Surface(
        color = Color(0xFFE0F2F1),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info, // Ícone de escudo (placeholder)
                contentDescription = null,
                tint = Color(0xFF00897B),
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.pet_tag_senior),
                color = Color(0xFF00897B),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// --- Previews ---
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun MyPetsScreenPreview() {
    VetiCareTheme {
        MyPetsScreen(
            pets = listOf(
                PetItemUiState(
                    id = "1",
                    name = "Fred",
                    breed = "fred",
                    age = 26,
                    weight = 22.0,
                    isSenior = true
                ),
                PetItemUiState(
                    id = "2",
                    name = "fred",
                    breed = "other",
                    age = 1,
                    weight = 5.0,
                    isSenior = false
                )
            ),
            onAddPetClick = {},
            onPetClick = {}
        )
    }
}