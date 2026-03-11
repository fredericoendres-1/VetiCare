package com.frmedev.veticare.ui.screens.addpet

data class AddPetUiState(
    val name: String = "",
    val breed: String = "",
    val birthdate: String = "",
    val weight: String = "",
    val selectedSpecies: String = "Dog" // No futuro, isso pode virar um Enum (Dog, Cat, Other)
)