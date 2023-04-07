package com.sosyal.app.di

import com.sosyal.app.domain.use_case.auth.LoginAccountUseCase
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.domain.use_case.post.GetPostUseCase
import com.sosyal.app.domain.use_case.post.UploadPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetAccessTokenUseCase
import com.sosyal.app.domain.use_case.user_credential.SaveAccessTokenUseCase
import org.koin.dsl.module

val domainModule = module {
    single { RegisterAccountUseCase(get()) }
    single { LoginAccountUseCase(get()) }
    single { SaveAccessTokenUseCase(get()) }
    single { GetAccessTokenUseCase(get()) }
    single { GetPostUseCase(get()) }
    single { UploadPostUseCase(get()) }
}