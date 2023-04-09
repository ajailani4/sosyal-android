package com.sosyal.app.di

import com.sosyal.app.domain.use_case.auth.LoginAccountUseCase
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import org.koin.dsl.module

val domainModule = module {
    single { RegisterAccountUseCase(get()) }
    single { LoginAccountUseCase(get()) }
    single { GetUserCredentialUseCase(get()) }
    single { ReceivePostUseCase(get()) }
    single { SendPostUseCase(get()) }
    single { GetPostDetailUseCase(get()) }
    single { DeletePostUseCase(get()) }
}