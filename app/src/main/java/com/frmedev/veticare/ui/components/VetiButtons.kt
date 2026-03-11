package com.frmedev.veticare.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.theme.VetiCareTheme
import com.frmedev.veticare.ui.theme.VetiDarkBrown
import com.frmedev.veticare.ui.theme.VetiOlive
import com.frmedev.veticare.ui.theme.VetiOutline
import com.frmedev.veticare.ui.theme.VetiTextPrimary

private val VetiButtonDefaultModifier = Modifier
    .fillMaxWidth()
    .height(56.dp)
private val VetiButtonDefaultShape = RoundedCornerShape(16.dp)

@Composable
fun VetiPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = VetiOlive),
        shape = VetiButtonDefaultShape,
        modifier = modifier.then(VetiButtonDefaultModifier)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun VetiAuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isOutlined: Boolean = false
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.then(VetiButtonDefaultModifier),
            shape = VetiButtonDefaultShape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = VetiTextPrimary),
            border = BorderStroke(1.dp, VetiOutline)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier.then(VetiButtonDefaultModifier),
            shape = VetiButtonDefaultShape,
            colors = ButtonDefaults.buttonColors(containerColor = VetiDarkBrown)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// --- Previews ---

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VetiButtonsPreview() {
    VetiCareTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            VetiPrimaryButton(
                text = stringResource(id = R.string.add_pet_btn_save),
                onClick = {}
            )
            VetiAuthButton(
                text = stringResource(id = R.string.auth_btn_google),
                onClick = {}
            )
            VetiAuthButton(
                text = stringResource(id = R.string.auth_btn_email),
                isOutlined = true,
                onClick = {}
            )
        }
    }
}