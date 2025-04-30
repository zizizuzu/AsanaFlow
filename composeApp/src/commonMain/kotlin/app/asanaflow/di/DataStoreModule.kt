package app.asanaflow.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.module.Module

expect fun dataStoreModule(): Module

const val dataStoreFileName = "cm.preferences_pb"

typealias PrefsDataStore = DataStore<Preferences>

fun createDataStore(
    producePath: () -> String
): PrefsDataStore = PreferenceDataStoreFactory.createWithPath(
    produceFile = { producePath().toPath() }
)
