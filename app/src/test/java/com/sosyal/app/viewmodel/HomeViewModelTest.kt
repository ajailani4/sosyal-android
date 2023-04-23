package com.sosyal.app.viewmodel

import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.screen.home.HomeEvent
import com.sosyal.app.ui.screen.home.HomeViewModel
import com.sosyal.app.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getUserCredentialUseCase: GetUserCredentialUseCase

    @Mock
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Mock
    private lateinit var receivePostUseCase: ReceivePostUseCase

    @Mock
    private lateinit var sendPostUseCase: SendPostUseCase

    @Mock
    private lateinit var deletePostUseCase: DeletePostUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun `Get user credential should be success`() {
        testCoroutineRule.runTest {
            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val username = homeViewModel.username

            assertEquals("Username should be 'george_z'", "george_z", username)
        }
    }

    @Test
    fun `Get user profile should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(userProfile))

            doReturn(resource).`when`(getUserProfileUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val userProfile = when (val userProfileState = homeViewModel.userProfileState) {
                is UIState.Success -> userProfileState.data

                else -> null
            }

            assertNotNull(userProfile)
            assertEquals("User avatar should be 'avatar_url'", "avatar_url", userProfile?.avatar)
        }
    }

    @Test
    fun `Get user profile should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<UserProfile>())

            doReturn(resource).`when`(getUserProfileUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val isSuccess = when (homeViewModel.userProfileState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Receive post should be success`() {
        testCoroutineRule.runTest {
            val _postSharedFlow = MutableSharedFlow<Post>()
            val postSharedFlow = _postSharedFlow.asSharedFlow()

            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()
            doReturn(postSharedFlow).`when`(receivePostUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val job = launch {
                postSharedFlow.collect()
            }

            _postSharedFlow.emit(post)

            val posts = homeViewModel.posts

            assertEquals("Posts size should be 1", 1, posts.size)

            job.cancel()
        }
    }

    @Test
    fun `Delete post should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success<JsonObject>())

            doReturn(resource).`when`(deletePostUseCase)(anyString())

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val onEvent = homeViewModel::onEvent
            onEvent(HomeEvent.OnPostSelected(post))
            onEvent(HomeEvent.DeletePost)

            val isSuccess = when (homeViewModel.deletePostState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Delete post should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<JsonObject>())

            doReturn(resource).`when`(deletePostUseCase)(anyString())

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val onEvent = homeViewModel::onEvent
            onEvent(HomeEvent.OnPostSelected(post))
            onEvent(HomeEvent.DeletePost)

            val isSuccess = when (homeViewModel.deletePostState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}