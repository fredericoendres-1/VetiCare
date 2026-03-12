package com.frmedev.veticare.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.theme.VetiCareTheme
import com.frmedev.veticare.ui.theme.VetiCardSurface
import com.frmedev.veticare.ui.theme.VetiOlive
import com.frmedev.veticare.ui.theme.VetiPeach
import com.frmedev.veticare.ui.theme.VetiTextPrimary
import com.frmedev.veticare.ui.theme.VetiTextSecondary

@Composable
fun DashboardGridCard(
    title: String,
    subtitle: String?,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = VetiCardSurface
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(VetiOlive.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = VetiOlive,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            color = VetiTextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        if (subtitle != null) {
            Text(
                text = subtitle,
                color = VetiTextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun QuickActionItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(VetiCardSurface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = VetiTextSecondary,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = VetiTextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun DailyVitalityCard(
    onLogVitality: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var energy by remember { mutableFloatStateOf(3f) }
    var appetite by remember { mutableFloatStateOf(3f) }
    var mobility by remember { mutableFloatStateOf(3f) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(VetiCardSurface)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = VetiOlive,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.home_vitality_title),
                color = VetiTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        VitalitySliderRow(
            label = stringResource(id = R.string.home_vitality_energy),
            value = energy,
            onValueChange = { energy = it },
            icon = Icons.Default.Info // Placeholder for energy icon
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        VitalitySliderRow(
            label = stringResource(id = R.string.home_vitality_appetite),
            value = appetite,
            onValueChange = { appetite = it },
            icon = Icons.Default.Info // Placeholder for appetite icon
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        VitalitySliderRow(
            label = stringResource(id = R.string.home_vitality_mobility),
            value = mobility,
            onValueChange = { mobility = it },
            icon = Icons.Default.Info // Placeholder for mobility icon
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLogVitality(energy.toInt(), appetite.toInt(), mobility.toInt()) },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VetiOlive),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_vitality_log_btn),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VitalitySliderRow(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    icon: ImageVector
) {
    val sliderState = remember {
        SliderState(
            value = value,
            valueRange = 1f..5f,
            steps = 3
        )
    }

    // Sincroniza o estado interno se o valor externo mudar
    LaunchedEffect(value) {
        if (sliderState.value != value) {
            sliderState.value = value
        }
    }

    // Notifica a mudança de valor durante o arraste
    val stateValue = sliderState.value
    LaunchedEffect(stateValue) {
        onValueChange(stateValue)
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = VetiTextPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = VetiTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = "${value.toInt()}/5",
                color = VetiTextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Slider(
            state = sliderState,
            thumb = {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.White, CircleShape)
                        .border(2.dp, VetiOlive, CircleShape)
                )
            },
            track = { state ->
                SliderDefaults.Track(
                    sliderState = state,
                    thumbTrackGapSize = 0.dp,
                    drawTick = { _, _ -> }, // Remove os pontinhos brancos (tick marks)
                    colors = SliderDefaults.colors(
                        activeTrackColor = VetiOlive,
                        inactiveTrackColor = VetiPeach.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.height(10.dp)
                )
            },
            modifier = Modifier.fillMaxWidth().height(32.dp)
        )
    }
}

// --- Previews ---

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun HomeComponentsPreview() {
    VetiCareTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardGridCard(
                    title = "Next Vaccine",
                    subtitle = "None scheduled",
                    icon = Icons.Default.Info,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                QuickActionItem(
                    label = "Weight",
                    icon = Icons.Default.Info,
                    onClick = {}
                )
            }
            DailyVitalityCard(onLogVitality = { _, _, _ -> })
        }
    }
}