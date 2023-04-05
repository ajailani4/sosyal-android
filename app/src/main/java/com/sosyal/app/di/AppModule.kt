package com.sosyal.app.di

import android.util.Log
import com.sosyal.app.BuildConfig
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
import com.sosyal.app.domain.use_case.auth.LoginAccountUseCase
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.domain.use_case.post.GetPostUseCase
import com.sosyal.app.domain.use_case.post.SubmitPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetAccessTokenUseCase
import com.sosyal.app.domain.use_case.user_credential.SaveAccessTokenUseCase
import com.sosyal.app.ui.screen.home.HomeViewModel
import com.sosyal.app.ui.screen.login.LoginViewModel
import com.sosyal.app.ui.screen.register.RegisterViewModel
import com.sosyal.app.ui.screen.splash.SplashViewModel
import com.sosyal.app.util.AuthInterceptor
import com.sosyal.app.util.Constants
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named(Constants.BaseUrl.HTTPS)) {
        HttpClient(OkHttp) {
            engine {
                config {
                    addInterceptor(AuthInterceptor(get()))
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor Log: ", message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }

            defaultRequest {
                url(BuildConfig.BASE_URL)
            }
        }
    }

    single(named(Constants.BaseUrl.WS)) {
        HttpClient(OkHttp) {
            engine {
                config {
                    addInterceptor(AuthInterceptor(get()))
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor Log: ", message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }

            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }

            defaultRequest {
                url(BuildConfig.WS_BASE_URL)
            }
        }
    }

    single { PreferencesDataStore(androidContext()) }

    // Coroutine Dispatcher
    single(named(Constants.CoroutineDispatcher.IO_DISPATCHER)) { Dispatchers.IO }

    // Service
    single { AuthService(get(named(Constants.BaseUrl.HTTPS))) }
    single {
        PostService(
            get(named(Constants.BaseUrl.WS)),
            get(named(Constants.CoroutineDispatcher.IO_DISPATCHER))
        )
    }

    // Remote Data Source
    single { AuthRemoteDataSource(get()) }
    single { PostRemoteDataSource(get()) }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get(), androidContext()) }
    single<UserCredentialRepository> { UserCredentialRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }

    // Use Case
    single { RegisterAccountUseCase(get()) }
    single { LoginAccountUseCase(get()) }
    single { SaveAccessTokenUseCase(get()) }
    single { GetAccessTokenUseCase(get()) }
    single { GetPostUseCase(get()) }
    single { SubmitPostUseCase(get()) }

    // ViewModel
    viewModel { SplashViewModel(get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
}
