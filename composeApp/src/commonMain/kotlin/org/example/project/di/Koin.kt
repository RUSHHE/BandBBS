package org.example.project.di

import org.example.project.viewmodel.DiscoverViewModel
import org.example.project.viewmodel.HomeViewModel
import org.example.project.viewmodel.ProfileViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::DiscoverViewModel)
    factoryOf(::ProfileViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            viewModelModule,
        )
    }
}
