package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.PostRepository
import com.example.domain.model.Home
import com.example.domain.model.PostItem
import com.example.domain.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostDetailsUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(uuid: String): Flow<State<PostItem>> {
        return postRepository.getPostDetails(uuid).map { state ->
            when (state) {
                is StateDto.Success -> {
                    state.data?.let { postDto ->
                        State.Success(PostItem.fromPostItemDto(postDto))
                    } ?: State.Error("HomeDto is null")
                }

                is StateDto.Error -> State.Error(state.message)
                is StateDto.Loading -> State.Loading
            }
        }
    }
}
