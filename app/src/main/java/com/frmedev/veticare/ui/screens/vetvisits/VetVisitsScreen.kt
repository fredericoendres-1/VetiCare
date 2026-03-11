package com.frmedev.veticare.ui.screens.vetvisits

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

// 1. Definição do Estado da Tela (State Hoisting) focado em Consultas
data class VetVisitItemUiState(
    val id: String,
    val title: String,      // Ex: "Medico bexiga"
    val date: String,       // Ex: "1555-12-22"
    val doctorName: String  // Ex: "Dr. Andre"
)

@Composable
fun VetVisitsScreen(
    visits: List<VetVisitItemUiState>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (String) -> Unit, // Recebe o ID da consulta
    onDeleteClick: (String) -> Unit // Recebe o ID da consulta
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .statusBarsPadding()
    ) {
        // Nosso Componente Reutilizado com o título correto!
        VetiTopBar(
            title = stringResource(id = R.string.vet_visits_title),
            onBackClick = onBackClick,
            onAddClick = onAddClick
        )

        // Lista de Consultas
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = visits,
                key = { it.id } // Otimização de performance
            ) { visit ->
                VetiListItemCard(
                    title = visit.title,
                    subtitle = stringResource(
                        id = R.string.vet_visit_subtitle_format,
                        visit.date,
                        visit.doctorName
                    ),
                    icon = {
                        // Ícone do Calendário (Substitua pelo seu drawable correto)
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_today), // Placeholder
                            contentDescription = stringResource(id = R.string.cd_vet_visit_icon),
                            tint = VetiOlive
                        )
                    },
                    onEditClick = { onEditClick(visit.id) },
                    onDeleteClick = { onDeleteClick(visit.id) }
                )
            }
        }
    }
}

// --- Previews ---

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VetVisitsScreenPreview() {
    VetiCareTheme {
        VetVisitsScreen(
            visits = listOf(
                VetVisitItemUiState(
                    id = "1",
                    title = "Medico bexiga",
                    date = "1555-12-22",
                    doctorName = "Dr. Andre"
                ),
                VetVisitItemUiState(
                    id = "2",
                    title = "Rotina Anual",
                    date = "2024-05-10",
                    doctorName = "Dra. Paula"
                )
            ),
            onBackClick = {},
            onAddClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}