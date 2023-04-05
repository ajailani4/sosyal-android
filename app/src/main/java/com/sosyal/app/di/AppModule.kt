package com.sosyal.app.di

import com.sosyal.app.util.Constants
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    includes(
        networkModule,
        dataModule,
        domainModule,
        viewModelModule
    )

    // Coroutine Dispatcher
    single(named(Constants.CoroutineDispatcher.IO_DISPATCHER)) { Dispatchers.IO }
}
