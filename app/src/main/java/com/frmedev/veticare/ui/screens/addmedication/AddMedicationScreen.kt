package com.frmedev.veticare.ui.screens.addmedication

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

data class AddMedicationUiState(
    val step: Int = 1, // 1 to 3
    val name: String = "",
    val dosage: String = "",
    val frequency: String = "Once per day",
    val startDate: String = "",
    val endDate: String = "",
    val reminderTime: String = "",
    val criticalNotifications: Boolean = false,
    val notes: String = ""
) {
    val isStep1Valid = name.isNotBlank() && dosage.isNotBlank()
    val isStep2Valid = startDate.isNotBlank() && reminderTime.isNotBlank()
}

// --- Main Screen ---
@Composable
fun AddMedicationScreen(
    uiState: AddMedicationUiState,
    onBackClick: () -> Unit,
    onNextStepClick: () -> Unit,
    onSaveClick: () -> Unit,
    onStateChange: (AddMedicationUiState) -> Unit // Para atualizar os campos
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        // --- Top Bar & Progress ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Button
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

            // Progress Bar
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 1..3) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                if (i <= uiState.step) VetiOlive else VetiOutline.copy(alpha = 0.5f)
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Animated Content for Steps ---
        AnimatedContent(
            targetState = uiState.step,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            },
            label = "step_transition"
        ) { targetStep ->
            when (targetStep) {
                1 -> Step1Content(
                    uiState = uiState,
                    onStateChange = onStateChange,
                    onContinue = onNextStepClick
                )
                2 -> Step2Content(
                    uiState = uiState,
                    onStateChange = onStateChange,
                    onContinue = onNextStepClick
                )
                3 -> Step3Content(
                    uiState = uiState,
                    onStateChange = onStateChange,
                    onSave = onSaveClick
                )
            }
        }
    }
}

// --- Steps Composables ---

@Composable
private fun Step1Content(
    uiState: AddMedicationUiState,
    onStateChange: (AddMedicationUiState) -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.add_med_title_1),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = VetiTextPrimary
        )
        Text(
            text = stringResource(id = R.string.add_med_subtitle_1),
            fontSize = 16.sp,
            color = VetiTextSecondary,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        VetiTextField(
            label = stringResource(id = R.string.add_med_name_label),
            value = uiState.name,
            onValueChange = { onStateChange(uiState.copy(name = it)) },
            placeholder = stringResource(id = R.string.add_med_name_hint)
        )

        Spacer(modifier = Modifier.height(16.dp))

        VetiTextField(
            label = stringResource(id = R.string.add_med_dosage_label),
            value = uiState.dosage,
            onValueChange = { onStateChange(uiState.copy(dosage = it)) },
            placeholder = stringResource(id = R.string.add_med_dosage_hint)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.add_med_frequency_label),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = VetiTextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Frequency Chips
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val options = listOf(
                stringResource(id = R.string.add_med_freq_once),
                stringResource(id = R.string.add_med_freq_twice),
                stringResource(id = R.string.add_med_freq_three),
                stringResource(id = R.string.add_med_freq_custom)
            )

            options.forEach { opt ->
                val isSelected = uiState.frequency == opt
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (isSelected) VetiPeach else Color.Transparent)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) VetiOlive else VetiOutline,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { onStateChange(uiState.copy(frequency = opt)) }
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = opt,
                        color = if (isSelected) Color.White else VetiTextSecondary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        VetiButton(
            text = stringResource(id = R.string.add_med_btn_continue),
            onClick = onContinue,
            enabled = uiState.isStep1Valid
        )
    }
}

@Composable
private fun Step2Content(
    uiState: AddMedicationUiState,
    onStateChange: (AddMedicationUiState) -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.add_med_title_2),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = VetiTextPrimary
        )
        Text(
            text = stringResource(id = R.string.add_med_subtitle_2),
            fontSize = 16.sp,
            color = VetiTextSecondary,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            VetiTextField(
                label = stringResource(id = R.string.add_med_start_date_label),
                value = uiState.startDate,
                onValueChange = { onStateChange(uiState.copy(startDate = it)) },
                placeholder = stringResource(id = R.string.add_med_date_hint),
                trailingIcon = "📅",
                modifier = Modifier.weight(1f)
            )
            VetiTextField(
                label = stringResource(id = R.string.add_med_end_date_label),
                value = uiState.endDate,
                onValueChange = { onStateChange(uiState.copy(endDate = it)) },
                placeholder = stringResource(id = R.string.add_med_date_hint),
                trailingIcon = "📅",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        VetiTextField(
            label = stringResource(id = R.string.add_med_reminder_label),
            value = uiState.reminderTime,
            onValueChange = { onStateChange(uiState.copy(reminderTime = it)) },
            placeholder = stringResource(id = R.string.add_med_reminder_hint),
            trailingIcon = "🕒"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🔔", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.add_med_critical_notif),
                    fontWeight = FontWeight.SemiBold,
                    color = VetiTextPrimary,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = uiState.criticalNotifications,
                    onCheckedChange = { onStateChange(uiState.copy(criticalNotifications = it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = VetiOlive,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = VetiOutline
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        VetiButton(
            text = stringResource(id = R.string.add_med_btn_continue),
            onClick = onContinue,
            enabled = uiState.isStep2Valid
        )
    }
}

@Composable
private fun Step3Content(
    uiState: AddMedicationUiState,
    onStateChange: (AddMedicationUiState) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.add_med_title_3),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = VetiTextPrimary
        )
        Text(
            text = stringResource(id = R.string.add_med_subtitle_3),
            fontSize = 16.sp,
            color = VetiTextSecondary,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        VetiTextField(
            label = "", // No label above the box in the design
            value = uiState.notes,
            onValueChange = { onStateChange(uiState.copy(notes = it)) },
            placeholder = stringResource(id = R.string.add_med_notes_hint),
            minLines = 5,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        VetiButton(
            text = stringResource(id = R.string.add_med_btn_save),
            onClick = onSave,
            enabled = true
        )
    }
}

// --- Reusable Private Components for this screen ---

@Composable
private fun VetiTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    trailingIcon: String? = null,
    minLines: Int = 1
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = VetiTextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = VetiTextSecondary.copy(alpha = 0.6f)) },
            shape = RoundedCornerShape(24.dp),
            minLines = minLines,
            trailingIcon = {
                if (trailingIcon != null) {
                    Text(text = trailingIcon, fontSize = 20.sp)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = VetiOutline.copy(alpha = 0.3f), // Very light border like the image
                focusedBorderColor = VetiOlive,
                unfocusedTextColor = VetiTextPrimary,
                focusedTextColor = VetiTextPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun VetiButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = VetiOlive.copy(alpha = 0.7f), // Similar to the light olive in the image
            disabledContainerColor = VetiOlive.copy(alpha = 0.3f),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// --- Previews ---

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun Step1Preview() {
    VetiCareTheme {
        AddMedicationScreen(
            uiState = AddMedicationUiState(step = 1),
            onBackClick = {}, onNextStepClick = {}, onSaveClick = {}, onStateChange = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun Step2Preview() {
    VetiCareTheme {
        AddMedicationScreen(
            uiState = AddMedicationUiState(step = 2),
            onBackClick = {}, onNextStepClick = {}, onSaveClick = {}, onStateChange = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun Step3Preview() {
    VetiCareTheme {
        AddMedicationScreen(
            uiState = AddMedicationUiState(step = 3),
            onBackClick = {}, onNextStepClick = {}, onSaveClick = {}, onStateChange = {}
        )
    }
}