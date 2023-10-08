package com.sosyal.app.data.repository

import android.content.Context
import com.sosyal.app.R
import com.sosyal.app.data.mapper.toComment
import com.sosyal.app.data.mapper.toCommentDto
import com.sosyal.app.data.remote.data_source.CommentDataSource
import com.sosyal.app.data.remote.dto.CommentDto
import com.sosyal.app.data.remote.dto.response.BaseResponse
import com.sosyal.app.domain.model.Comment
import com.sosyal.app.domain.repository.CommentRepository
import com.sosyal.app.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommentRepositoryImpl(
    private val commentDataSource: CommentDataSource,
    private val context: Context
) : CommentRepository {
    override fun getComments(postId: String) =
        flow {
            val response = commentDataSource.getComments(postId)

            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.body<BaseResponse<List<CommentDto>>>()
                    emit(Resource.Success(responseBody.data?.map { commentDto -> commentDto.toComment() }))
                }

                HttpStatusCode.InternalServerError -> emit(Resource.Error(context.getString(R.string.server_error)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun receiveComment(postId: String) = commentDataSource.receiveComment(postId)

    override suspend fun sendComment(comment: Comment) {
        commentDataSource.sendComment(comment.toCommentDto())
    }
}