package com.frmedev.veticare.ui.screens.healthlog

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
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
enum class HealthLogCategory(val stringResId: Int, val iconEmoji: String) {
    INSECT_BITE(R.string.health_log_cat_insect, "🐛"),
    VOMITING(R.string.health_log_cat_vomiting, "🤮"),
    DIARRHEA(R.string.health_log_cat_diarrhea, "💩"),
    LIMPING(R.string.health_log_cat_limping, "🦿"),
    OTHER(R.string.health_log_cat_other, "❓")
}

data class HealthLogUiState(
    val category: HealthLogCategory = HealthLogCategory.INSECT_BITE,
    val notes: String = ""
) {
    val isValid = notes.isNotBlank()
}

// --- Main Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthLogScreen(
    uiState: HealthLogUiState,
    onStateChange: (HealthLogUiState) -> Unit,
    onBackClick: () -> Unit,
    onAttachPhotoClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
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
                text = stringResource(id = R.string.health_log_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = VetiTextPrimary
            )
        }

        // --- Main Form Card ---
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFE6EFEB), // Light teal/blue background from image
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                
                // Category Label
                Text(
                    text = stringResource(id = R.string.health_log_category_label),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VetiTextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { dropdownExpanded = it }
                ) {
                    OutlinedTextField(
                        value = "${uiState.category.iconEmoji}  ${stringResource(id = uiState.category.stringResId)}",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = VetiCardSurface,
                            focusedContainerColor = VetiCardSurface,
                            unfocusedBorderColor = VetiOlive,
                            focusedBorderColor = VetiOlive
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        HealthLogCategory.entries.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = category.iconEmoji)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = stringResource(id = category.stringResId))
                                    }
                                },
                                onClick = {
                                    onStateChange(uiState.copy(category = category))
                                    dropdownExpanded = false
                                },
                                trailingIcon = if (category == uiState.category) {
                                    { Icon(Icons.Default.Check, contentDescription = null, tint = VetiOlive) }
                                } else null,
                                modifier = if (category == uiState.category) Modifier.background(VetiOlive.copy(alpha = 0.5f)) else Modifier
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Alert Message
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Black.copy(alpha = 0.05f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = VetiTextSecondary,
                            modifier = Modifier.size(16.dp).offset(y = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.health_log_alert_msg),
                            fontSize = 13.sp,
                            color = VetiTextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Notes Label
                Text(
                    text = stringResource(id = R.string.health_log_notes_label),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VetiTextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Notes Input
                OutlinedTextField(
                    value = uiState.notes,
                    onValueChange = { onStateChange(uiState.copy(notes = it)) },
                    placeholder = { 
                        Text(
                            text = stringResource(id = R.string.health_log_notes_hint),
                            color = VetiTextSecondary.copy(alpha = 0.7f)
                        ) 
                    },
                    minLines = 5,
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = VetiCardSurface,
                        focusedContainerColor = VetiCardSurface,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = VetiOlive
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Attach Photo Button
                Button(
                    onClick = onAttachPhotoClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VetiCardSurface,
                        contentColor = Color(0xFF4A9094) // Teal color from image
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(text = "📷", fontSize = 18.sp) // Camera placeholder
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.health_log_btn_attach),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = onSaveClick,
                    enabled = uiState.isValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A9094), // Teal background
                        disabledContainerColor = Color(0xFF4A9094).copy(alpha = 0.5f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.health_log_btn_save),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun HealthLogScreenPreview() {
    VetiCareTheme {
        var uiState by remember { mutableStateOf(HealthLogUiState()) }

        HealthLogScreen(
            uiState = uiState,
            onStateChange = { uiState = it },
            onBackClick = {},
            onAttachPhotoClick = {},
            onSaveClick = {}
        )
    }
}