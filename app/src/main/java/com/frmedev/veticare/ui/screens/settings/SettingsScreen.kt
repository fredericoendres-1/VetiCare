package com.frmedev.veticare.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.theme.*

// --- UI State ---
data class SettingsUiState(
    val language: String = "English",
    val unitSystem: String = "Metric (kg/g)",
    val dateFormat: String = "DD/MM/YYYY"
)

// --- Main Screen ---
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBackClick: () -> Unit,
    onLanguageChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onDateFormatChange: (String) -> Unit,
    onNotificationSettingsClick: () -> Unit,
    onExportPdfClick: () -> Unit,
    onHelpClick: () -> Unit
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
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
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.settings_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = VetiTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Settings Bento Cards ---

        // Language
        SettingsSectionCard(
            title = stringResource(id = R.string.settings_lang_title),
            icon = Icons.Default.Build // Placeholder for Globe
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    stringResource(id = R.string.settings_lang_en),
                    stringResource(id = R.string.settings_lang_pt),
                    stringResource(id = R.string.settings_lang_es)
                ).forEach { lang ->
                    SelectionChip(
                        label = lang,
                        isSelected = uiState.language == lang,
                        onClick = { onLanguageChange(lang) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Units
        SettingsSectionCard(
            title = stringResource(id = R.string.settings_units_title),
            icon = Icons.Default.Create // Placeholder for Ruler
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    stringResource(id = R.string.settings_units_metric),
                    stringResource(id = R.string.settings_units_imperial)
                ).forEach { unit ->
                    SelectionChip(
                        label = unit,
                        isSelected = uiState.unitSystem == unit,
                        onClick = { onUnitChange(unit) },
                        modifier = Modifier.weight(1f) // Expands equally
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Date Format
        SettingsSectionCard(
            title = stringResource(id = R.string.settings_date_title),
            icon = Icons.Default.DateRange
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    stringResource(id = R.string.settings_date_format_1),
                    stringResource(id = R.string.settings_date_format_2)
                ).forEach { format ->
                    SelectionChip(
                        label = format,
                        isSelected = uiState.dateFormat == format,
                        onClick = { onDateFormatChange(format) },
                        modifier = Modifier.weight(1f) // Expands equally
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Action List ---
        SettingsActionRow(
            title = stringResource(id = R.string.settings_notif_title),
            icon = Icons.Default.Notifications,
            trailingIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            onClick = onNotificationSettingsClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingsActionRow(
            title = stringResource(id = R.string.settings_export_title),
            subtitle = stringResource(id = R.string.settings_export_subtitle),
            icon = Icons.AutoMirrored.Filled.List, // Placeholder for document
            trailingIcon = Icons.Default.KeyboardArrowDown, // Placeholder for download
            onClick = onExportPdfClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingsActionRow(
            title = stringResource(id = R.string.settings_help_title),
            icon = Icons.Default.Info, // Placeholder for Help
            trailingIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            onClick = onHelpClick
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// --- Reusable Components for this screen ---

@Composable
private fun SettingsSectionCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = VetiTextPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VetiTextPrimary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun SelectionChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) VetiOlive else Color(0xFFF3EFE9) // Very light beige from image
    val textColor = if (isSelected) Color.White else VetiTextSecondary

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SettingsActionRow(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    trailingIcon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = VetiTextPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = VetiTextPrimary
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = VetiTextSecondary
                    )
                }
            }
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = if (subtitle != null) VetiOlive else VetiTextSecondary.copy(alpha = 0.5f), // Matching the image where PDF export arrow is green
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun SettingsScreenPreview() {
    VetiCareTheme {
        var uiState by remember { mutableStateOf(SettingsUiState()) }

        SettingsScreen(
            uiState = uiState,
            onBackClick = {},
            onLanguageChange = { uiState = uiState.copy(language = it) },
            onUnitChange = { uiState = uiState.copy(unitSystem = it) },
            onDateFormatChange = { uiState = uiState.copy(dateFormat = it) },
            onNotificationSettingsClick = {},
            onExportPdfClick = {},
            onHelpClick = {}
        )
    }
}