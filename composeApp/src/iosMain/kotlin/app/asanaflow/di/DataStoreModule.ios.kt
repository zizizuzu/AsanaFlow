package app.asanaflow.di

import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun dataStoreModule(): Module = module {
    single<PrefsDataStore> {
        createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager().URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$dataStoreFileName"
            }
        )
    }
}