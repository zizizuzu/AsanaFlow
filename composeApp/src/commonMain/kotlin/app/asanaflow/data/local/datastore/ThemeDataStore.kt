package app.asanaflow.data.local.datastore

import app.asanaflow.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeDataStore {
    val themeModeFlow: Flow<ThemeMode>

    suspend fun changeTheme(mode: ThemeMode)
}