package com.sosyal.app.di

import android.util.Log
import com.sosyal.app.BuildConfig
import com.sosyal.app.data.remote.api_service.AuthService
import com.sosyal.app.data.remote.data_source.AuthRemoteDataSource
import com.sosyal.app.data.repository.AuthRepositoryImpl
import com.sosyal.app.domain.repository.AuthRepository
import com.sosyal.app.domain.use_case.auth.LoginAccountUseCase
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.ui.screen.register.RegisterViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(CIO) {
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

    // Service
    single { AuthService(get()) }

    // Remote Data Source
    single { AuthRemoteDataSource(get()) }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get(), androidContext()) }

    // Use Case
    single { RegisterAccountUseCase(get()) }
    single { LoginAccountUseCase(get()) }

    // ViewModel
    viewModel { RegisterViewModel(get()) }
}
