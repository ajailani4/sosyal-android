package com.sosyal.app.di

import com.sosyal.app.BuildConfig
import com.sosyal.app.data.remote.api_service.AuthService
import com.sosyal.app.data.remote.data_source.AuthRemoteDataSource
import com.sosyal.app.data.repository.AuthRepositoryImpl
import com.sosyal.app.domain.repository.AuthRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(CIO) {
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

    single { AuthService(get()) }
    single { AuthRemoteDataSource(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), androidContext()) }
}
