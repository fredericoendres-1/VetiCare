package com.frmedev.veticare.ui.screens.petdetail

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun PetDetailScreen(
    petName: String,
    breed: String,
    age: String,
    weight: String,
    species: String,
    onBackClick: () -> Unit,
    onExportPdfClick: () -> Unit,
    onMenuClick: (String) -> Unit,
    onDeletePetClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        // --- Top Bar ---
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(48.dp)
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

        // --- Profile Header Card ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Large
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(VetiBackground)
                        .border(2.dp, VetiOutline.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Face, // Placeholder dog icon
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = VetiTextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = petName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = VetiTextPrimary
                )
                Text(
                    text = breed,
                    fontSize = 16.sp,
                    color = VetiTextSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Info Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InfoItem(value = age, label = stringResource(id = R.string.pet_detail_age_label))
                    InfoItem(value = weight, label = stringResource(id = R.string.pet_detail_weight_label))
                    InfoItem(value = species, label = stringResource(id = R.string.pet_detail_species_label))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Export PDF Card ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExportPdfClick() },
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFEFECE5) // Light beige/olive tint from image
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange, // Placeholder for PDF icon
                    contentDescription = null,
                    tint = VetiOlive,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.pet_detail_export_title),
                        fontWeight = FontWeight.SemiBold,
                        color = VetiTextPrimary,
                        fontSize = 15.sp
                    )
                    Text(
                        text = stringResource(id = R.string.pet_detail_export_subtitle),
                        color = VetiTextSecondary,
                        fontSize = 13.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, // Placeholder for download icon
                    contentDescription = null,
                    tint = VetiOlive,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Navigation Menu ---
        NavigationRow(
            icon = "💊", 
            label = stringResource(id = R.string.pet_detail_menu_medications),
            onClick = { onMenuClick("Medications") }
        )
        NavigationRow(
            icon = "💉", 
            label = stringResource(id = R.string.pet_detail_menu_vaccines),
            onClick = { onMenuClick("Vaccines") }
        )
        NavigationRow(
            icon = "📅", 
            label = stringResource(id = R.string.pet_detail_menu_vet_visits),
            onClick = { onMenuClick("VetVisits") }
        )
        NavigationRow(
            icon = "⚖️", 
            label = stringResource(id = R.string.pet_detail_menu_weight_history),
            onClick = { onMenuClick("WeightHistory") }
        )
        NavigationRow(
            icon = "🩺", 
            label = stringResource(id = R.string.pet_detail_menu_symptom_log),
            onClick = { onMenuClick("SymptomLog") }
        )
        NavigationRow(
            icon = "🔐", 
            label = stringResource(id = R.string.pet_detail_menu_document_vault),
            onClick = { onMenuClick("DocumentVault") }
        )
        NavigationRow(
            icon = "📋", 
            label = stringResource(id = R.string.pet_detail_menu_health_timeline),
            onClick = { onMenuClick("HealthTimeline") }
        )
        NavigationRow(
            icon = "✂️",
            label = stringResource(id = R.string.pet_detail_delete_btn),
            onClick = { onDeletePetClick() }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun InfoItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = VetiTextPrimary
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = VetiTextSecondary
        )
    }
}

@Composable
private fun NavigationRow(
    icon: String, // Emoji
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                color = VetiTextPrimary,
                fontSize = 15.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = VetiTextSecondary.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun PetDetailScreenPreview() {
    VetiCareTheme {
        PetDetailScreen(
            petName = "fre",
            breed = "fr",
            age = "-54345 months",
            weight = "56kg",
            species = "Dog",
            onBackClick = {},
            onExportPdfClick = {},
            onMenuClick = {},
            onDeletePetClick = {}
        )
    }
}