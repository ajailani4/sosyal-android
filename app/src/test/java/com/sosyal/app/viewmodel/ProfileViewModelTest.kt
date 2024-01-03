package com.sosyal.app.viewmodel

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.domain.use_case.user_credential.DeleteUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.screen.profile.ProfileEvent
import com.sosyal.app.ui.screen.profile.ProfileViewModel
import com.sosyal.app.util.Result
import com.sosyal.app.util.TestCoroutineRule
import com.sosyal.app.util.userProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Mock
    private lateinit var deleteUserCredentialUseCase: DeleteUserCredentialUseCase

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var onEvent: (ProfileEvent) -> Unit

    @Before
    fun setUp() {
        profileViewModel = ProfileViewModel(getUserProfileUseCase, deleteUserCredentialUseCase)
        onEvent = profileViewModel::onEvent
    }

    @Test
    fun `Get user profile should be success`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Success(userProfile))

            doReturn(result).`when`(getUserProfileUseCase)()

            onEvent(ProfileEvent.GetUserProfile)

            val userProfile = when (val userProfileState = profileViewModel.userProfileState) {
                is UIState.Success -> userProfileState.data

                else -> null
            }

            assertNotNull(userProfile)
            assertEquals("Username should be 'george_z'", "george_z", userProfile?.username)
        }
    }

    @Test
    fun `Get user profile should be fail`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Error<UserProfile>())

            doReturn(result).`when`(getUserProfileUseCase)()

            onEvent(ProfileEvent.GetUserProfile)

            val isSuccess = when (profileViewModel.userProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}