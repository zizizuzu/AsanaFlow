package app.asanaflow.data.local.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import app.asanaflow.di.PrefsDataStore
import app.asanaflow.domain.model.ThemeMode
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ThemeDataStoreImpl(
    private val dataStore: PrefsDataStore
) : ThemeDataStore {

    override val themeModeFlow: Flow<ThemeMode> = dataStore.data.map {
        val theme = it[stringPreferencesKey(KEY_THEME_MODE)] ?: ThemeMode.AUTO.name
        ThemeMode.valueOf(theme)
    }.flowOn(Dispatchers.IO)

    override suspend fun changeTheme(mode: ThemeMode) {
        try {
            dataStore.edit {
                it[stringPreferencesKey(KEY_THEME_MODE)] = mode.name
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
        }
    }

    private companion object {
        const val KEY_THEME_MODE = "key_theme_mode"
    }
}