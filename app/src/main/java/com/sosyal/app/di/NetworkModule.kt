package com.sosyal.app.di

import android.util.Log
import com.sosyal.app.BuildConfig
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
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
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
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }

            install(WebSockets)

            defaultRequest {
                url(BuildConfig.WS_BASE_URL)
            }
        }
    }
}