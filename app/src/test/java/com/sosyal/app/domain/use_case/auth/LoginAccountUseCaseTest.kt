package com.sosyal.app.domain.use_case.auth

import com.sosyal.app.domain.model.UserCredential
import com.sosyal.app.domain.repository.AuthRepositoryFake
import com.sosyal.app.util.Resource
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
class LoginAccountUseCaseTest {
    private lateinit var authRepositoryFake: AuthRepositoryFake
    private lateinit var loginAccountUseCase: LoginAccountUseCase

    @Before
    fun setUp() {
        authRepositoryFake = AuthRepositoryFake()
        loginAccountUseCase = LoginAccountUseCase(authRepositoryFake)
    }

    @Test
    fun `Login should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = loginAccountUseCase(
                username = "george_z",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userCredential),
                actualResource
            )
        }

    @Test
    fun `Login should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            authRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = loginAccountUseCase(
                username = "george_z",
                password = "123"
            ).first()

            assertEquals(
                "Resource should be success",
                Resource.Error<UserCredential>(),
                actualResource
            )
        }
}