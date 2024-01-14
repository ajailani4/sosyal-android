package com.sosyal.app.viewmodel

import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.model.UserProfile
import com.sosyal.app.domain.use_case.post.DeletePostUseCase
import com.sosyal.app.domain.use_case.post.GetPostsUseCase
import com.sosyal.app.domain.use_case.post.ReceivePostUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.domain.use_case.user_profile.GetUserProfileUseCase
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
    private lateinit var getPostsUseCase: GetPostsUseCase

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
                getPostsUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            assertEquals(
                "Username should be 'george_z'",
                "george_z",
                homeViewModel.uiState.username
            )
        }
    }

    @Test
    fun `Get user profile should be success`() {
        testCoroutineRule.runTest {
            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()
            doReturn(flowOf(ApiResult.Success(userProfile))).`when`(getUserProfileUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                getPostsUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            with(homeViewModel.uiState) {
                assertNotNull(userProfile)
                assertEquals(
                    "User avatar should be 'avatar_url'",
                    "avatar_url",
                    userProfile?.avatar
                )
            }
        }
    }

    @Test
    fun `Get user profile should be fail`() {
        testCoroutineRule.runTest {
            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()
            doReturn(flowOf(ApiResult.Error<UserProfile>())).`when`(getUserProfileUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                getPostsUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            with(homeViewModel.uiState) {
                val isSuccess = userProfile != null && errorMessage == null

                assertEquals("Should be fail", false, isSuccess)
            }
        }
    }

    @Test
    fun `Get and receive posts should be success`() {
        testCoroutineRule.runTest {
            val _postSharedFlow = MutableSharedFlow<Post>()
            val postSharedFlow = _postSharedFlow.asSharedFlow()

            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()
            doReturn(flowOf(ApiResult.Success(posts))).`when`(getPostsUseCase)()
            doReturn(postSharedFlow).`when`(receivePostUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                getPostsUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            with(homeViewModel.uiState) {
                val job = launch {
                    postSharedFlow.collect()
                }

                _postSharedFlow.emit(post)

                assertEquals("Posts should not empty", posts.isNotEmpty(), true)
                assertEquals("Posts size should be 1", 3, posts.size)

                job.cancel()
            }
        }
    }

    @Test
    fun `Get posts should be fail`() {
        testCoroutineRule.runTest {
            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()
            doReturn(flowOf(ApiResult.Error<List<Post>>())).`when`(getPostsUseCase)()

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                getPostsUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            with(homeViewModel.uiState) {
                val isFailed = isPostsLoading == false && errorMessage != null

                assertEquals("Should be failed", false, isFailed)
            }
        }
    }

    @Test
    fun `Delete post should be success`() {
        testCoroutineRule.runTest {
            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()
            doReturn(flowOf(ApiResult.Success<JsonObject>())).`when`(deletePostUseCase)(anyString())

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                getPostsUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val onEvent = homeViewModel::onEvent
            onEvent(HomeEvent.OnPostSelected(post))
            onEvent(HomeEvent.DeletePost)

            with(homeViewModel.uiState) {
                val isSuccess = isPostDeleting == false && errorMessage == null

                assertEquals("Should be success", true, isSuccess)
            }
        }
    }

    @Test
    fun `Delete post should be fail`() {
        testCoroutineRule.runTest {
            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()
            doReturn(flowOf(ApiResult.Error<JsonObject>())).`when`(deletePostUseCase)(anyString())

            homeViewModel = HomeViewModel(
                getUserCredentialUseCase,
                getUserProfileUseCase,
                getPostsUseCase,
                receivePostUseCase,
                sendPostUseCase,
                deletePostUseCase
            )

            val onEvent = homeViewModel::onEvent
            onEvent(HomeEvent.OnPostSelected(post))
            onEvent(HomeEvent.DeletePost)

            with(homeViewModel.uiState) {
                val isFailed = isPostDeleting == false && errorMessage != null

                assertEquals("Should be failed", false, isFailed)
            }
        }
    }
}