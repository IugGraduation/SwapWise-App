package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.PostRepository
import com.example.domain.model.Home
import com.example.domain.model.PostItem
import com.example.domain.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(searchValue: String): Flow<State<List<PostItem>>> {
        return postRepository.searchPosts(searchValue).map { state ->
            when (state) {
                is StateDto.Success -> {
                    state.data?.let { postItemDtoList ->
                        val postItemList = postItemDtoList.filterNotNull().map {
                                PostItem.fromPostItemDto(it)
                        }
                        State.Success(postItemList)
                    } ?: State.Success(listOf())
                }

                is StateDto.Error -> State.Error(state.message)
                is StateDto.Loading -> State.Loading
            }
        }
    }
}
