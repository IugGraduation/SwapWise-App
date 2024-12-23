package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.HomeRepository
import com.example.domain.model.Home
import com.example.domain.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Flow<State<Home>> {
        return homeRepository.getHomeData().map { state ->
            when (state) {
                is StateDto.Success -> {
                    state.data?.let { homeDto ->
                        State.Success(Home.fromHomeDto(homeDto))
                    } ?: State.Error("HomeDto is null")
                }

                is StateDto.Error -> State.Error(state.message)
                is StateDto.Loading -> State.Loading
            }
        }
    }
}
