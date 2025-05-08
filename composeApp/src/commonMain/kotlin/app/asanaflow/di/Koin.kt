package app.asanaflow.di

import app.asanaflow.data.local.datastore.ThemeDataStore
import app.asanaflow.data.local.datastore.ThemeDataStoreImpl
import app.asanaflow.data.repository.DaysRepositoryImpl
import app.asanaflow.di.scopes.ScheduleScope
import app.asanaflow.domain.repository.DaysRepository
import app.asanaflow.domain.usecase.LoadDayScheduleUseCase
import app.asanaflow.domain.usecase.LoadDayScheduleUseCaseImpl
import app.asanaflow.presentation.screens.schedule.ScheduleViewModel
import app.asanaflow.presentation.screens.settings.SettingsViewModel
import app.asanaflow.presentation.screens.schedule.mapper.DayMapper
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single<DaysRepository> { DaysRepositoryImpl() }
    single<ThemeDataStore> { ThemeDataStoreImpl(get()) }
}

val domainModule = module {
    single<LoadDayScheduleUseCase> { LoadDayScheduleUseCaseImpl(get()) }
}

val presentationModule = module {
    scope<ScheduleScope> {
        scoped { ScheduleViewModel(get(), get()) }
    }
    factory { DayMapper() }
    factoryOf(::SettingsViewModel)
}

fun initKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(dataModule, domainModule, presentationModule, dataStoreModule())
    }
}