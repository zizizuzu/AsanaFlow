package app.asanaflow.di

import app.asanaflow.data.repository.DaysRepositoryImpl
import app.asanaflow.domain.repository.DaysRepository
import app.asanaflow.domain.usecase.LoadDayScheduleUseCase
import app.asanaflow.domain.usecase.LoadDayScheduleUseCaseImpl
import app.asanaflow.presentation.screens.schedule.ScheduleViewModel
import app.asanaflow.presentation.screens.schedule.mapper.DayMapper
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single<DaysRepository> { DaysRepositoryImpl() }
//    factory { MyViewModel(get()) }
}


val domainModule = module {
    single<LoadDayScheduleUseCase> { LoadDayScheduleUseCaseImpl(get()) }
//    factory { MyViewModel(get()) }
}

val presentationModule = module {
    factory { DayMapper() }
    factoryOf(::ScheduleViewModel)
}

fun initKoin() {
    startKoin {
        modules(dataModule, domainModule, presentationModule)
    }
}