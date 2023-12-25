package com.sosyal.app.domain.use_case.post

import com.sosyal.app.domain.repository.PostRepositoryFake
import com.sosyal.app.util.Result
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
class DeletePostDetailUseCaseTest {
    private lateinit var postRepositoryFake: PostRepositoryFake
    private lateinit var deletePostUseCase: DeletePostUseCase

    @Before
    fun setUp() {
        postRepositoryFake = PostRepositoryFake()
        deletePostUseCase = DeletePostUseCase(postRepositoryFake)
    }

    @Test
    fun `Get post detail should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            postRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = deletePostUseCase("1").first()

            assertEquals(
                "Resource should be success",
                Result.Success<JsonObject>(),
                actualResource
            )
        }

    @Test
    fun `Get post detail should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            postRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = deletePostUseCase("1").first()

            assertEquals(
                "Resource should be success",
                Result.Error<JsonObject>(),
                actualResource
            )
        }
}