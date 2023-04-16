package com.sosyal.app.di

import com.sosyal.app.data.local.PreferencesDataStore
import com.sosyal.app.data.remote.api_service.AuthService
import com.sosyal.app.data.remote.api_service.CommentService
import com.sosyal.app.data.remote.api_service.PostService
import com.sosyal.app.data.remote.api_service.UserProfileService
import com.sosyal.app.data.remote.data_source.AuthRemoteDataSource
import com.sosyal.app.data.remote.data_source.CommentDataSource
import com.sosyal.app.data.remote.data_source.PostRemoteDataSource
import com.sosyal.app.data.remote.data_source.UserProfileRemoteDataSource
import com.sosyal.app.data.repository.*
import com.sosyal.app.domain.repository.*
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
            get(named(Constants.BaseUrl.HTTPS)),
            get(named(Constants.CoroutineDispatcher.IO_DISPATCHER))
        )
    }
    single {
        CommentService(
            get(named(Constants.BaseUrl.WS)),
            get(named(Constants.CoroutineDispatcher.IO_DISPATCHER))
        )
    }
    single { UserProfileService(get(named(Constants.BaseUrl.HTTPS))) }

    // DataSource
    single { PreferencesDataStore(androidContext()) }
    single { AuthRemoteDataSource(get()) }
    single { PostRemoteDataSource(get()) }
    single { CommentDataSource(get()) }
    single { UserProfileRemoteDataSource(get()) }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), androidContext()) }
    single<UserCredentialRepository> { UserCredentialRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), androidContext()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
    single<UserProfileRepository> { UserProfileRepositoryImpl(get(), androidContext()) }
}