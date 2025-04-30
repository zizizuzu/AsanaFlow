package app.asanaflow

import android.app.Application
import app.asanaflow.di.initKoin
import org.koin.android.ext.koin.androidContext

class AsanaFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AsanaFlowApp)
        }
    }
}