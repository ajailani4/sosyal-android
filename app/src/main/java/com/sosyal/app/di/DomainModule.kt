package com.sosyal.app.di

import com.sosyal.app.domain.use_case.auth.LoginAccountUseCase
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import org.koin.dsl.module

val domainModule = module {
    // Auth
    single { RegisterAccountUseCase(get()) }
    single { LoginAccountUseCase(get()) }

    // User Credential
    single { GetUserCredentialUseCase(get()) }

    // Post
    single { ReceivePostUseCase(get()) }
    single { SendPostUseCase(get()) }
    single { GetPostDetailUseCase(get()) }
    single { DeletePostUseCase(get()) }

    // User Profile
    single { GetUserProfileUseCase(get()) }
}