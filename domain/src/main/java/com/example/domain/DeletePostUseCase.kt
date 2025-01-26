package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.PostRepository
import com.example.domain.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DeletePostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String): Flow<State<Boolean>> {
        return postRepository.deletePost(postId).map { state ->
            when (state) {
                is StateDto.Success -> State.Success(true)
                is StateDto.Error -> State.Error(state.message)
                is StateDto.Loading -> State.Loading
            }
        }
    }
}
