package com.frmedev.veticare.ui.screens.documentvault

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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.theme.*

// --- UI State & Enums ---
enum class DocumentCategory(val stringResId: Int, val iconEmoji: String) {
    PRESCRIPTION(R.string.doc_category_prescription, "💊"),
    EXAM(R.string.doc_category_exam, "🔬"),
    REPORT(R.string.doc_category_report, "📋"),
    INCIDENT(R.string.doc_category_incident, "📸"),
    OTHER(R.string.doc_category_other, "📄")
}

data class DocumentItemUiState(
    val id: String,
    val name: String,
    val date: String,
    val category: DocumentCategory,
    val fileType: String // e.g., "PDF", "JPG"
)

// --- Main Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentVaultScreen(
    documents: List<DocumentItemUiState>,
    onBackClick: () -> Unit,
    onUploadClick: (DocumentCategory) -> Unit,
    onDocumentClick: (String) -> Unit
) {
    // State for the upload category dropdown
    var selectedUploadCategory by remember { mutableStateOf(DocumentCategory.OTHER) }
    var uploadDropdownExpanded by remember { mutableStateOf(false) }

    // Filters state
    var selectedDateFilter by remember { mutableStateOf<String?>(null) }
    var selectedCategoryFilter by remember { mutableStateOf<DocumentCategory?>(null) }

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
                .padding(horizontal = 24.dp, vertical = 16.dp),
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
            Icon(
                imageVector = Icons.Default.Lock, // Shield/Vault placeholder
                contentDescription = null,
                tint = Color(0xFF5E9C9F), // Teal color from image
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.document_vault_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = VetiTextPrimary
            )
        }

        // --- Upload Card ---
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.doc_upload_card_title),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VetiTextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = uploadDropdownExpanded,
                    onExpandedChange = { uploadDropdownExpanded = it }
                ) {
                    OutlinedTextField(
                        value = "${selectedUploadCategory.iconEmoji}  ${stringResource(id = selectedUploadCategory.stringResId)}",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = uploadDropdownExpanded)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = VetiCardSurface,
                            focusedContainerColor = VetiCardSurface,
                            unfocusedBorderColor = VetiOlive, // Border olive colored in image
                            focusedBorderColor = VetiOlive
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = uploadDropdownExpanded,
                        onDismissRequest = { uploadDropdownExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DocumentCategory.entries.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = category.iconEmoji)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = stringResource(id = category.stringResId))
                                    }
                                },
                                onClick = {
                                    selectedUploadCategory = category
                                    uploadDropdownExpanded = false
                                },
                                trailingIcon = if (category == selectedUploadCategory) {
                                    { Icon(Icons.Default.Check, contentDescription = null, tint = VetiOlive) }
                                } else null,
                                modifier = if (category == selectedUploadCategory) Modifier.background(VetiOlive.copy(alpha = 0.5f)) else Modifier
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Upload Button
                Button(
                    onClick = { onUploadClick(selectedUploadCategory) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A9094) // Teal matching the image
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp, // Upload icon placeholder
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.doc_upload_btn),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Filters (Optional behavior requested) ---
        if (documents.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Simple simulated filter chips
                FilterChipItem(
                    label = stringResource(id = R.string.doc_filter_date_hint),
                    isSelected = selectedDateFilter != null,
                    onClick = { selectedDateFilter = if (selectedDateFilter == null) "Recent" else null },
                    icon = Icons.Default.DateRange
                )
                FilterChipItem(
                    label = stringResource(id = R.string.doc_filter_category_hint),
                    isSelected = selectedCategoryFilter != null,
                    onClick = { selectedCategoryFilter = if (selectedCategoryFilter == null) DocumentCategory.EXAM else null },
                    icon = Icons.AutoMirrored.Filled.List
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // --- Documents List or Empty State ---
        if (documents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Lock, // Shield/Vault Outline placeholder
                        contentDescription = null,
                        tint = VetiOutline.copy(alpha = 0.5f),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.doc_empty_title),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = VetiTextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.doc_empty_subtitle),
                        fontSize = 14.sp,
                        color = VetiTextSecondary.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = documents, key = { it.id }) { doc ->
                    DocumentItemCard(
                        document = doc,
                        onClick = { onDocumentClick(doc.id) }
                    )
                }
            }
        }
    }
}

// --- Subcomponents ---

@Composable
private fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) VetiOlive else Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) VetiOlive else VetiOutline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = if (isSelected) Color.White else VetiTextSecondary
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            color = if (isSelected) Color.White else VetiTextSecondary,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
    }
}

@Composable
fun DocumentItemCard(
    document: DocumentItemUiState,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(VetiCardSurface),
                contentAlignment = Alignment.Center
            ) {
                Text(text = document.category.iconEmoji, fontSize = 20.sp)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = document.name,
                    color = VetiTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = document.date,
                        color = VetiTextSecondary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = " • ${stringResource(id = document.category.stringResId)}",
                        color = VetiTextSecondary,
                        fontSize = 13.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEFECE5))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = document.fileType,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = VetiTextSecondary
                )
            }
        }
    }
}

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun DocumentVaultScreenEmptyPreview() {
    VetiCareTheme {
        DocumentVaultScreen(
            documents = emptyList(),
            onBackClick = {},
            onUploadClick = {},
            onDocumentClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun DocumentVaultScreenListPreview() {
    VetiCareTheme {
        DocumentVaultScreen(
            documents = listOf(
                DocumentItemUiState(
                    id = "1",
                    name = "Blood Test Results",
                    date = "Oct 12, 2023",
                    category = DocumentCategory.EXAM,
                    fileType = "PDF"
                ),
                DocumentItemUiState(
                    id = "2",
                    name = "NexGard Prescription",
                    date = "Sep 05, 2023",
                    category = DocumentCategory.PRESCRIPTION,
                    fileType = "JPG"
                )
            ),
            onBackClick = {},
            onUploadClick = {},
            onDocumentClick = {}
        )
    }
}