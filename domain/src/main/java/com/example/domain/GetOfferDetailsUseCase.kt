package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.OfferRepository
import com.example.domain.model.OfferItem
import com.example.domain.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOfferDetailsUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(uuid: String): Flow<State<OfferItem>> {
        return offerRepository.getOfferDetails(uuid).map { state ->
            when (state) {
                is StateDto.Success -> {
                    state.data?.let { offerItemDto ->
                        State.Success(OfferItem.fromOfferItemDto(offerItemDto))
                    } ?: State.Error("HomeDto is null")
                }

                is StateDto.Error -> State.Error(state.message)
                is StateDto.Loading -> State.Loading
            }
        }
    }
}
