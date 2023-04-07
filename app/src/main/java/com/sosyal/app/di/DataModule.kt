package com.sosyal.app.di

import com.sosyal.app.data.local.PreferencesDataStore
import com.sosyal.app.data.remote.api_service.AuthService
import com.sosyal.app.data.remote.api_service.PostService
import com.sosyal.app.data.remote.data_source.AuthRemoteDataSource
import com.sosyal.app.data.remote.data_source.PostRemoteDataSource
import com.sosyal.app.data.repository.AuthRepositoryImpl
import com.sosyal.app.data.repository.PostRepositoryImpl
import com.sosyal.app.data.repository.UserCredentialRepositoryImpl
import com.sosyal.app.domain.repository.AuthRepository
import com.sosyal.app.domain.repository.PostRepository
import com.sosyal.app.domain.repository.UserCredentialRepository
import com.sosyal.app.util.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    // API Service
    single { AuthService(get(named(Constants.BaseUrl.HTTPS))) }
    single {
        PostService(
            get(named(Constants.BaseUrl.WS)),
            get(named(Constants.CoroutineDispatcher.IO_DISPATCHER))
        )
    }

    // DataSource
    single { PreferencesDataStore(androidContext()) }
    single { AuthRemoteDataSource(get()) }
    single { PostRemoteDataSource(get()) }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), androidContext()) }
    single<UserCredentialRepository> { UserCredentialRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
}