package com.sosyal.app.domain.use_case.profile

import com.sosyal.app.domain.repository.UserProfileRepositoryFake
import com.sosyal.app.domain.use_case.user_profile.EditUserProfileUseCase
import com.sosyal.app.util.ApiResult
import com.sosyal.app.util.ResourceType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EditUserProfileUseCaseTest {
    private lateinit var userProfileRepositoryFake: UserProfileRepositoryFake
    private lateinit var editUserProfileUseCase: EditUserProfileUseCase

    @Before
    fun setUp() {
        userProfileRepositoryFake = UserProfileRepositoryFake()
        editUserProfileUseCase = EditUserProfileUseCase(userProfileRepositoryFake)
    }

    @Test
    fun `Get user profile should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = editUserProfileUseCase(
                name = "George",
                email = "george@email.com",
                avatar = null
            ).first()

            assertEquals(
                "Resource should be success",
                ApiResult.Success<JsonObject>(),
                actualResource
            )
        }

    @Test
    fun `Get user profile should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = editUserProfileUseCase(
                name = "George",
                email = "george@email.com",
                avatar = null
            ).first()

            assertEquals(
                "Resource should be success",
                ApiResult.Error<JsonObject>(),
                actualResource
            )
        }
}