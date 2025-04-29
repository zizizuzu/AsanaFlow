package app.asanaflow

import androidx.compose.ui.window.ComposeUIViewController
import app.asanaflow.di.initKoin
import app.asanaflow.presentation.screens.main.MainScreen

fun MainViewController() = ComposeUIViewController {
    initKoin()
    MainScreen()
}