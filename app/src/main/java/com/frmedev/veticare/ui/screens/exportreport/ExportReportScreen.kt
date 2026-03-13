package com.frmedev.veticare.ui.screens.exportreport

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
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
enum class ReportDateRange(val stringResId: Int) {
    LAST_30_DAYS(R.string.export_report_range_30_days),
    LAST_6_MONTHS(R.string.export_report_range_6_months),
    ALL_TIME(R.string.export_report_range_all_time)
}

// --- Main Screen ---
@Composable
fun ExportReportScreen(
    onBackClick: () -> Unit,
    onGeneratePdfClick: (ReportDateRange) -> Unit
) {
    var selectedRange by remember { mutableStateOf(ReportDateRange.LAST_30_DAYS) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Top Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
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
                text = stringResource(id = R.string.export_report_topbar_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = VetiTextPrimary
            )
        }

        // --- Main Info Card ---
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(VetiCardSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "📄", fontSize = 28.sp) // Document icon placeholder
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(id = R.string.export_report_main_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = VetiTextPrimary,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = stringResource(id = R.string.export_report_main_desc),
                    fontSize = 14.sp,
                    color = VetiTextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Date Range Selector Card ---
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.export_report_date_range_label),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VetiTextPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ReportDateRange.entries.forEach { range ->
                        DateRangeChip(
                            label = stringResource(id = range.stringResId),
                            isSelected = selectedRange == range,
                            onClick = { selectedRange = range }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Includes Info Card ---
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.export_report_include_label),
                    fontSize = 14.sp,
                    color = VetiTextSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val includesList = listOf(
                    R.string.export_report_include_meds,
                    R.string.export_report_include_vaccines,
                    R.string.export_report_include_visits,
                    R.string.export_report_include_weight,
                    R.string.export_report_include_symptoms
                )

                includesList.forEach { stringResId ->
                    Text(
                        text = stringResource(id = stringResId),
                        fontSize = 14.sp,
                        color = VetiTextPrimary,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Generate PDF Button ---
        Button(
            onClick = { onGeneratePdfClick(selectedRange) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VetiOlive
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, // Placeholder for download icon
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.export_report_generate_btn),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// --- Subcomponents ---

@Composable
private fun DateRangeChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) VetiOlive else VetiCardSurface
    val textColor = if (isSelected) Color.White else VetiTextSecondary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

// --- Previews ---
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun ExportReportScreenPreview() {
    VetiCareTheme {
        ExportReportScreen(
            onBackClick = {},
            onGeneratePdfClick = {}
        )
    }
}