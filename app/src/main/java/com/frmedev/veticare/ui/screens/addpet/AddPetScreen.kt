package com.frmedev.veticare.ui.screens.addpet

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.components.VetiPrimaryButton
import com.frmedev.veticare.ui.components.VetiTextField
import com.frmedev.veticare.ui.theme.*

@Composable
fun AddPetScreen(
    uiState: AddPetUiState,
    onNameChange: (String) -> Unit,
    onBreedChange: (String) -> Unit,
    onBirthdateChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onSpeciesSelect: (String) -> Unit,
    onPhotoClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .padding(horizontal = 24.dp)
            .padding(top = 48.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Títulos
        Text(
            text = stringResource(id = R.string.add_pet_title),
            color = VetiTextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = stringResource(id = R.string.add_pet_subtitle),
            color = VetiTextSecondary,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // Foto do Pet (Placeholder Câmera)
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.padding(bottom = 32.dp).clickable { onPhotoClick() }
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, VetiOutline, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_camera),
                    contentDescription = stringResource(id = R.string.add_pet_cd_camera),
                    tint = VetiTextSecondary,
                    modifier = Modifier.size(40.dp)
                )
            }
            // Botãozinho verde sobreposto
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .offset(x = (-4).dp, y = 4.dp)
                    .clip(CircleShape)
                    .background(VetiOlive),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_camera),
                    contentDescription = stringResource(id = R.string.add_pet_cd_add_photo),
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Formulário
        VetiTextField(
            label = stringResource(id = R.string.add_pet_name_label),
            value = uiState.name,
            onValueChange = onNameChange,
            hint = stringResource(id = R.string.add_pet_name_hint),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botões de Espécie
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_pet_species_label),
                color = VetiTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SpeciesButton(
                    text = stringResource(id = R.string.add_pet_species_dog),
                    isSelected = uiState.selectedSpecies == stringResource(id = R.string.add_pet_species_dog),
                    onClick = { onSpeciesSelect("Dog") }, // "Dog" aqui seria a key interna
                    modifier = Modifier.weight(1f)
                )
                SpeciesButton(
                    text = stringResource(id = R.string.add_pet_species_cat),
                    isSelected = uiState.selectedSpecies == stringResource(id = R.string.add_pet_species_cat),
                    onClick = { onSpeciesSelect("Cat") },
                    modifier = Modifier.weight(1f)
                )
                SpeciesButton(
                    text = stringResource(id = R.string.add_pet_species_other),
                    isSelected = uiState.selectedSpecies == stringResource(id = R.string.add_pet_species_other),
                    onClick = { onSpeciesSelect("Other") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        VetiTextField(
            label = stringResource(id = R.string.add_pet_breed_label),
            value = uiState.breed,
            onValueChange = onBreedChange,
            hint = stringResource(id = R.string.add_pet_breed_hint),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        ) {
            VetiTextField(
                label = stringResource(id = R.string.add_pet_birthdate_label),
                value = uiState.birthdate,
                onValueChange = onBirthdateChange,
                hint = stringResource(id = R.string.add_pet_birthdate_hint),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(id = R.string.add_pet_cd_calendar),
                        tint = VetiTextPrimary
                    )
                },
                modifier = Modifier.weight(1f)
            )
            VetiTextField(
                label = stringResource(id = R.string.add_pet_weight_label),
                value = uiState.weight,
                onValueChange = onWeightChange,
                hint = stringResource(id = R.string.add_pet_weight_hint),
                modifier = Modifier.weight(0.7f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        VetiPrimaryButton(
            text = stringResource(id = R.string.add_pet_btn_save),
            onClick = onSaveClick
        )
    }
}

// O Ideal seria mover este componente para ui/components também, mas mantive aqui para escopo.
@Composable
fun SpeciesButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (isSelected) Color(0xFFEFECE5) else Color.Transparent
    val borderColor = if (isSelected) VetiOlive else VetiOutline

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = VetiTextPrimary
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        modifier = modifier.height(48.dp)
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}

// --- Previews ---

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AddPetScreenPreview() {
    VetiCareTheme {
        AddPetScreen(
            uiState = AddPetUiState(
                name = "Max",
                breed = "Golden",
                selectedSpecies = "Dog"
            ),
            onNameChange = {},
            onBreedChange = {},
            onBirthdateChange = {},
            onWeightChange = {},
            onSpeciesSelect = {},
            onPhotoClick = {},
            onSaveClick = {}
        )
    }
}