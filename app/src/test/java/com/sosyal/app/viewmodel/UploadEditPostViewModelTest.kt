package com.sosyal.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.post.SendPostUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.screen.upload_edit_post.UploadEditPostEvent
import com.sosyal.app.ui.screen.upload_edit_post.UploadEditPostViewModel
import com.sosyal.app.util.Result
import com.sosyal.app.util.TestCoroutineRule
import com.sosyal.app.util.post
import com.sosyal.app.util.userCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UploadEditPostViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var sendPostUseCase: SendPostUseCase

    @Mock
    private lateinit var getUserCredentialUseCase: GetUserCredentialUseCase

    @Mock
    private lateinit var getPostDetailUseCase: GetPostDetailUseCase

    private lateinit var uploadEditPostViewModel: UploadEditPostViewModel

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply { set("postId", "1") }
    }

    @Test
    fun `Get post detail should be success`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Success(post))

            doReturn(result).`when`(getPostDetailUseCase)(anyString())

            uploadEditPostViewModel = UploadEditPostViewModel(
                savedStateHandle,
                sendPostUseCase,
                getUserCredentialUseCase,
                getPostDetailUseCase
            )

            val content = uploadEditPostViewModel.content

            assertEquals("Content should be 'Hello'", "Hello", content)
        }
    }

    @Test
    fun `Get post detail should be error`() {
        testCoroutineRule.runTest {
            val result = flowOf(Result.Error<Post>())

            doReturn(result).`when`(getPostDetailUseCase)(anyString())

            uploadEditPostViewModel = UploadEditPostViewModel(
                savedStateHandle,
                sendPostUseCase,
                getUserCredentialUseCase,
                getPostDetailUseCase
            )

            val isSuccess = when (uploadEditPostViewModel.postDetailState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Upload post should be success`() {
        testCoroutineRule.runTest {
            doReturn(flowOf(userCredential)).`when`(getUserCredentialUseCase)()

            uploadEditPostViewModel = UploadEditPostViewModel(
                savedStateHandle,
                sendPostUseCase,
                getUserCredentialUseCase,
                getPostDetailUseCase
            )

            uploadEditPostViewModel.onEvent(UploadEditPostEvent.UploadPost)

            val isSuccess = when (uploadEditPostViewModel.uploadPostState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Edit post should be success`() {
        testCoroutineRule.runTest {
            uploadEditPostViewModel = UploadEditPostViewModel(
                savedStateHandle,
                sendPostUseCase,
                getUserCredentialUseCase,
                getPostDetailUseCase
            )

            uploadEditPostViewModel.onEvent(UploadEditPostEvent.EditPost)

            val isSuccess = when (uploadEditPostViewModel.editPostState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }
}