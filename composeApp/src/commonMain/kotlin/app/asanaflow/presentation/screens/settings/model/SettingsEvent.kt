package app.asanaflow.presentation.screens.settings.model

sealed class SettingsEvent {
    data class ChangedTheme(val isDarkMode: Boolean) : SettingsEvent()
}