package com.sosyal.app.di

import com.sosyal.app.domain.use_case.auth.LoginAccountUseCase
import com.sosyal.app.domain.use_case.auth.RegisterAccountUseCase
import com.sosyal.app.domain.use_case.chat.GetChatsUseCase
import com.sosyal.app.domain.use_case.comment.GetCommentsUseCase
import com.sosyal.app.domain.use_case.comment.ReceiveCommentUseCase
import com.sosyal.app.domain.use_case.comment.SendCommentUseCase
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.post.GetPostsUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.DeleteUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.EditUserProfileUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import org.koin.dsl.module

val domainModule = module {
    // Auth
    single { RegisterAccountUseCase(get()) }
    single { LoginAccountUseCase(get()) }

    // User Credential
    single { GetUserCredentialUseCase(get()) }
    single { DeleteUserCredentialUseCase(get()) }

    // Post
    single { GetPostsUseCase(get()) }
    single { ReceivePostUseCase(get()) }
    single { SendPostUseCase(get()) }
    single { GetPostDetailUseCase(get()) }
    single { DeletePostUseCase(get()) }

    // Comment
    single { GetCommentsUseCase(get()) }
    single { ReceiveCommentUseCase(get()) }
    single { SendCommentUseCase(get()) }

    // User Profile
    single { GetUserProfileUseCase(get()) }
    single { EditUserProfileUseCase(get()) }

    // Chat
    single { GetChatsUseCase(get()) }
}