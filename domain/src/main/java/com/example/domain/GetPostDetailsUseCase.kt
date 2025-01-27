package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.PostRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.PostItem
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPostDetailsUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(uuid: String): PostItem {
        val state = postRepository.getPostDetails(uuid)
            .filter { it !is StateDto.Loading } // Filter out Loading states
            .first() // Wait for the first non-loading state

        return when (state) {
            is StateDto.Success -> {
                state.data?.let { postDto ->
                    PostItem.fromPostItemDto(postDto)
                } ?: throw EmptyDataException()
            }

            is StateDto.Error -> throw Exception(state.message)
            is StateDto.Loading -> throw IllegalStateException("Unexpected loading state")
        }
    }
}