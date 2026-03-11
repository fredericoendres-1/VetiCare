package com.frmedev.veticare.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.theme.*

@Composable
fun VetiTopBar(
    title: String,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Back Button
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(VetiCardSurface, CircleShape)
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.cd_back_button),
                tint = VetiTextPrimary
            )
        }

        // Title
        Text(
            text = title,
            color = VetiTextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        // Add Button
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(VetiOlive, CircleShape)
                .clickable { onAddClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.cd_add_button),
                tint = Color.White
            )
        }
    }
}

@Composable
fun VetiListItemCard(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(VetiCardSurface, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Box
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(VetiBackground, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = VetiTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = VetiTextSecondary,
                fontSize = 13.sp
            )
        }

        // 3 Dots Menu
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.cd_more_options),
                    tint = VetiTextSecondary
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.action_edit)) },
                    onClick = {
                        expanded = false
                        onEditClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.action_delete), color = MaterialTheme.colorScheme.error) },
                    onClick = {
                        expanded = false
                        onDeleteClick()
                    }
                )
            }
        }
    }
}

// --- Previews ---
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VetiListComponentsPreview() {
    VetiCareTheme {
        Column(modifier = Modifier.background(VetiBackground).padding(vertical = 16.dp)) {
            VetiTopBar(title = "Vaccines", onBackClick = {}, onAddClick = {})
            Spacer(modifier = Modifier.height(16.dp))
            VetiListItemCard(
                title = "Astrazaneca",
                subtitle = "Given: 1999-05-06 • Due: 2100-02-21",
                icon = { Text("💉") }, // Placeholder rápido para o preview
                onEditClick = {},
                onDeleteClick = {},
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}