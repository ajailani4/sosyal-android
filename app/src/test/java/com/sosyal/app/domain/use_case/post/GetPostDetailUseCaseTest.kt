package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.model.Post
import com.sosyal.app.domain.repository.PostRepositoryFake
import com.sosyal.app.util.Resource
import com.sosyal.app.util.ResourceType
import com.sosyal.app.util.post
import com.sosyal.app.util.userCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPostDetailUseCaseTest {
    private lateinit var postRepositoryFake: PostRepositoryFake
    private lateinit var getPostDetailUseCase: GetPostDetailUseCase

    @Before
    fun setUp() {
        postRepositoryFake = PostRepositoryFake()
        getPostDetailUseCase = GetPostDetailUseCase(postRepositoryFake)
    }

    @Test
    fun `Get post detail should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            postRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getPostDetailUseCase("1").first()

            assertEquals(
                "Resource should be success",
                Resource.Success(post),
                actualResource
            )
        }

    @Test
    fun `Get post detail should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            postRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getPostDetailUseCase("1").first()

            assertEquals(
                "Resource should be success",
                Resource.Error<Post>(),
                actualResource
            )
        }
}