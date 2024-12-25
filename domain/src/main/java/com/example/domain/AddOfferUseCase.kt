package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.OfferRepository
import com.example.domain.model.OfferItem
import com.example.domain.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AddOfferUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(postId: String, offerItem: OfferItem): Flow<State<Boolean>> {
        return offerRepository.addOffer(postId, offerItem.toOfferItemDto()).map { state ->
            when (state) {
                is StateDto.Success -> State.Success(true)
                is StateDto.Error -> State.Error(state.message)
                is StateDto.Loading -> State.Loading
            }
        }
    }
}
