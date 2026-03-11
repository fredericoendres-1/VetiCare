package com.frmedev.veticare.ui.screens.vaccines

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.components.VetiListItemCard
import com.frmedev.veticare.ui.components.VetiTopBar
import com.frmedev.veticare.ui.theme.VetiBackground
import com.frmedev.veticare.ui.theme.VetiCareTheme
import com.frmedev.veticare.ui.theme.VetiOlive

// 1. Definição do Estado da Tela (State Hoisting)
data class VaccineItemUiState(
    val id: String,
    val name: String,
    val dateGiven: String,
    val dateDue: String
)

@Composable
fun VaccinesScreen(
    vaccines: List<VaccineItemUiState>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (String) -> Unit, // Recebe o ID da vacina
    onDeleteClick: (String) -> Unit // Recebe o ID da vacina
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding() // Evita que a TopBar fique embaixo da barra de bateria do celular
    ) {
        // Nosso Componente Reutilizado!
        VetiTopBar(
            title = stringResource(id = R.string.vaccines_title),
            onBackClick = onBackClick,
            onAddClick = onAddClick
        )

        // O "RecyclerView" do Jetpack Compose
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = vaccines,
                key = { it.id } // Importante para performance da lista (como o id do diffutil)
            ) { vaccine ->
                VetiListItemCard(
                    title = vaccine.name,
                    subtitle = stringResource(
                        id = R.string.vaccine_subtitle_format,
                        vaccine.dateGiven,
                        vaccine.dateDue
                    ),
                    icon = {
                        // Ícone da Seringa (Substitua pelo seu drawable se tiver)
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_edit), // Placeholder
                            contentDescription = stringResource(id = R.string.cd_vaccine_icon),
                            tint = VetiOlive
                        )
                    },
                    onEditClick = { onEditClick(vaccine.id) },
                    onDeleteClick = { onDeleteClick(vaccine.id) }
                )
            }
        }
    }
}

// --- Previews ---
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VaccinesScreenPreview() {
    VetiCareTheme {
        VaccinesScreen(
            vaccines = listOf(
                VaccineItemUiState(
                    id = "1",
                    name = "Astrazaneca",
                    dateGiven = "1999-05-06",
                    dateDue = "2100-02-21"
                ),
                VaccineItemUiState(
                    id = "2",
                    name = "Anti-rabies",
                    dateGiven = "2023-10-10",
                    dateDue = "2024-10-10"
                )
            ),
            onBackClick = {},
            onAddClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}