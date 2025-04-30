package app.asanaflow.presentation.screens.settings

import androidx.lifecycle.viewModelScope
import app.asanaflow.data.local.datastore.ThemeDataStore
import app.asanaflow.domain.model.ThemeMode
import app.asanaflow.presentation.base.BaseViewModel
import app.asanaflow.presentation.screens.settings.model.SettingsEffect
import app.asanaflow.presentation.screens.settings.model.SettingsEvent
import app.asanaflow.presentation.screens.settings.model.SettingsState
import kotlinx.coroutines.launch

//TODO super early version
class SettingsViewModel constructor(
    private val dataStore: ThemeDataStore
) : BaseViewModel<SettingsEvent, SettingsState, SettingsEffect>() {

    fun init() {
        viewModelScope.launch {
            dataStore.themeModeFlow.collect {
                updateState {
                    copy(isDarkTheme = it == ThemeMode.DARK || it == ThemeMode.DYNAMIC_DARK)
                }
            }
        }

    }

    override fun setInitialState(): SettingsState = SettingsState()

    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangedTheme -> changeTheme(event.isDarkMode)
        }
    }

    private fun changeTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            dataStore.changeTheme(if (isDarkMode) ThemeMode.DARK else ThemeMode.LIGHT)
        }
    }
}