package com.sosyal.app.viewmodel

import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.domain.use_case.user_profile.EditUserProfileUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.screen.edit_profile.EditProfileEvent
import com.sosyal.app.ui.screen.edit_profile.EditProfileViewModel
import com.sosyal.app.util.Result
import com.sosyal.app.util.TestCoroutineRule
import com.sosyal.app.util.userProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.JsonObject
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.isNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditProfileViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Mock
    private lateinit var editUserProfileUseCase: EditUserProfileUseCase

    private lateinit var editProfileViewModel: EditProfileViewModel

    @Test
    fun `Get user profile should be success`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Success(userProfile))

            doReturn(result).`when`(getUserProfileUseCase)()

            editProfileViewModel = EditProfileViewModel(
                getUserProfileUseCase,
                editUserProfileUseCase
            )

            val isSuccess = when (editProfileViewModel.userProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Get user profile should be fail`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Error<UserProfile>())

            doReturn(result).`when`(getUserProfileUseCase)()

            editProfileViewModel = EditProfileViewModel(
                getUserProfileUseCase,
                editUserProfileUseCase
            )

            val isSuccess = when (editProfileViewModel.userProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Edit user profile should be success`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Success<JsonObject>())

            doReturn(result).`when`(editUserProfileUseCase)(
                name = anyString(),
                email = anyString(),
                avatar = anyOrNull()
            )

            editProfileViewModel = EditProfileViewModel(
                getUserProfileUseCase,
                editUserProfileUseCase
            )

            editProfileViewModel.onEvent(EditProfileEvent.EditProfile)

            val isSuccess = when (editProfileViewModel.editUserProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Edit user profile should be fail`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Error<JsonObject>())

            doReturn(result).`when`(editUserProfileUseCase)(
                name = anyString(),
                email = anyString(),
                avatar = isNull()
            )

            editProfileViewModel = EditProfileViewModel(
                getUserProfileUseCase,
                editUserProfileUseCase
            )

            editProfileViewModel.onEvent(EditProfileEvent.EditProfile)

            val isSuccess = when (editProfileViewModel.editUserProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}