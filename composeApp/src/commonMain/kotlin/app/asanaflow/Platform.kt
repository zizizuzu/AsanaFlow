package app.asanaflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform