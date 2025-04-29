package app.asanaflow

import android.app.Application
import app.asanaflow.di.initKoin

class AsanaFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}