package com.sosyal.app.domain.use_case.auth

import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.domain.repository.AuthRepositoryFake
import com.sosyal.app.util.ApiResult
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.userCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterAccountUseCaseTest {
    private lateinit var authRepositoryFake: AuthRepositoryFake
    private lateinit var registerAccountUseCase: RegisterAccountUseCase

    @Before
    fun setUp() {
        authRepositoryFake = AuthRepositoryFake()
        registerAccountUseCase = RegisterAccountUseCase(authRepositoryFake)
    }

    @Test
    fun `Register should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = registerAccountUseCase(
                name= "George",
                email = "george@email.com",
                username = "george_z",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be success",
                ApiResult.Success(userCredential),
                actualResource
            )
        }

    @Test
    fun `Register should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = registerAccountUseCase(
                name= "George",
                email = "george@email.com",
                username = "george_z",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be success",
                ApiResult.Error<UserCredential>(),
                actualResource
            )
        }
}