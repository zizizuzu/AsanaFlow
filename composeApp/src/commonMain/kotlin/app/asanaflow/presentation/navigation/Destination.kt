package app.asanaflow.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

sealed class Destination(
    val baseRoute: String,
    val pathArgs: List<String> = emptyList(),
    val queryArgs: List<String> = emptyList(),
) {
    data object Home : Destination("home")
    data object Settings : Destination("settings")
    data object PreviewTraining : Destination("preview_training")

//    data class TrainingDetails(val id: String) : Destination("training_details/$id") {
//        companion object {
//            fun fromRoute(route: String?): TrainingDetails? {
//                return route?.substringAfter("training_details/")?.let { TrainingDetails(it) }
//            }
//        }
//    }

    fun buildRoute(): String {
        val pathArgsPart = if (pathArgs.isNotEmpty()) {
            "/${pathArgs.joinToString("/") { "{$it}" }}"
        } else ""
        val queryArgsPart = if (queryArgs.isNotEmpty()) {
            "?${queryArgs.joinToString("&") { "$it={$it}" }}"
        } else ""
        return baseRoute + pathArgsPart + queryArgsPart
    }
}

fun NavController.navigateTo(destination: Destination) {
    navigate(destination.buildRoute())
}

fun NavController.navigateTo(destination: Destination, builder: NavOptionsBuilder.() -> Unit) {
    navigate(destination.baseRoute, builder)
}