package com.frmedev.veticare.ui.screens.home

// Representa um Pet simplificado para o seletor no topo
data class PetMini(
    val id: String,
    val name: String,
    val avatarUrl: String? = null // Pode ser usado com Coil no futuro
)

// O estado completo da tela Home
data class HomeUiState(
    val isLoading: Boolean = false,
    val pets: List<PetMini> = emptyList(),
    val selectedPetId: String? = null,
    val nextAction: String? = null, // null significa "No actions pending"
    val nextVaccineDate: String? = null,
    val nextVetVisitDate: String? = null,
    val hasLoggedVitalityToday: Boolean = false // Controla se a seção Daily Vitality deve aparecer
) {
    val selectedPetName: String
        get() = pets.find { it.id == selectedPetId }?.name ?: "Pet"
}