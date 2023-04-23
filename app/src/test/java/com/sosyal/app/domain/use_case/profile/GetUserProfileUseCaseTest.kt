package com.sosyal.app.domain.use_case.profile

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.domain.repository.UserProfileRepositoryFake
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.util.Resource
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.userProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserProfileUseCaseTest {
    private lateinit var userProfileRepositoryFake: UserProfileRepositoryFake
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Before
    fun setUp() {
        userProfileRepositoryFake = UserProfileRepositoryFake()
        getUserProfileUseCase = GetUserProfileUseCase(userProfileRepositoryFake)
    }

    @Test
    fun `Get user profile should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getUserProfileUseCase().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userProfile),
                actualResource
            )
        }

    @Test
    fun `Get user profile should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            userProfileRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getUserProfileUseCase().first()

            assertEquals(
                "Resource should be success",
                Resource.Error<UserProfile>(),
                actualResource
            )
        }
}