package com.frmedev.veticare.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.theme.VetiCareTheme
import com.frmedev.veticare.ui.theme.VetiCardSurface
import com.frmedev.veticare.ui.theme.VetiOlive
import com.frmedev.veticare.ui.theme.VetiOutline
import com.frmedev.veticare.ui.theme.VetiTextPrimary
import com.frmedev.veticare.ui.theme.VetiTextSecondary

@Composable
fun VetiTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = VetiTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = hint,
                    color = VetiTextSecondary
                )
            },
            trailingIcon = trailingIcon,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = VetiCardSurface,
                unfocusedContainerColor = VetiCardSurface,
                focusedBorderColor = VetiOlive,
                unfocusedBorderColor = VetiOutline,
                cursorColor = VetiOlive
            ),
            singleLine = true
        )
    }
}

// --- Previews ---

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VetiTextFieldPreview() {
    VetiCareTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            VetiTextField(
                label = stringResource(id = R.string.add_pet_name_label),
                value = "",
                onValueChange = {},
                hint = stringResource(id = R.string.add_pet_name_hint)
            )
        }
    }
}