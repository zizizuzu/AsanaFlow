package app.asanaflow.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun dataStoreModule(): Module = module {
    single<PrefsDataStore> {
        val context = get<Context>()
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                context.filesDir.resolve(dataStoreFileName).absolutePath.toPath()
            }
        )
    }
}