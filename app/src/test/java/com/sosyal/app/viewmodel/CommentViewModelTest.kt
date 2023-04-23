package com.sosyal.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.sosyal.app.domain.model.Comment
import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.use_case.comment.ReceiveCommentUseCase
import com.sosyal.app.domain.use_case.comment.SendCommentUseCase
import com.sosyal.app.domain.use_case.post.GetPostDetailUseCase
import com.sosyal.app.domain.use_case.user_credential.GetUserCredentialUseCase
import com.sosyal.app.ui.common.UIState
import com.sosyal.app.ui.screen.comments.CommentViewModel
import com.sosyal.app.util.Resource
import com.sosyal.app.util.TestCoroutineRule
import com.sosyal.app.util.comment
import com.sosyal.app.util.post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
class CommentViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getPostDetailUseCase: GetPostDetailUseCase

    @Mock
    private lateinit var getUserCredentialUseCase: GetUserCredentialUseCase

    @Mock
    private lateinit var receiveCommentUseCase: ReceiveCommentUseCase

    @Mock
    private lateinit var sendCommentUseCase: SendCommentUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var commentViewModel: CommentViewModel

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply { set("postId", "1") }
    }

    @Test
    fun `Get post detail should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(post))

            doReturn(resource).`when`(getPostDetailUseCase)(anyString())

            commentViewModel = CommentViewModel(
                savedStateHandle,
                getPostDetailUseCase,
                getUserCredentialUseCase,
                receiveCommentUseCase,
                sendCommentUseCase
            )

            val post = when (val postDetailState = commentViewModel.postDetailState) {
                is UIState.Success -> postDetailState.data

                else -> null
            }

            assertNotNull(post)
            assertEquals("Post id should be '1'", "1", post?.id)
        }
    }

    @Test
    fun `Get post detail should be error`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Post>())

            doReturn(resource).`when`(getPostDetailUseCase)(anyString())

            commentViewModel = CommentViewModel(
                savedStateHandle,
                getPostDetailUseCase,
                getUserCredentialUseCase,
                receiveCommentUseCase,
                sendCommentUseCase
            )

            val isSuccess = when (commentViewModel.postDetailState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Receive comment should be success`() {
        testCoroutineRule.runTest {
            val _commentSharedFlow = MutableSharedFlow<Comment>()
            val commentSharedFlow = _commentSharedFlow.asSharedFlow()

            doReturn(commentSharedFlow).`when`(receiveCommentUseCase)(anyString())

            commentViewModel = CommentViewModel(
                savedStateHandle,
                getPostDetailUseCase,
                getUserCredentialUseCase,
                receiveCommentUseCase,
                sendCommentUseCase
            )

            val job = launch {
                commentSharedFlow.collect()
            }

            _commentSharedFlow.emit(comment)

            val comments = commentViewModel.comments

            assertEquals("Comments size should be 1", 1, comments.size)

            job.cancel()
        }
    }
}