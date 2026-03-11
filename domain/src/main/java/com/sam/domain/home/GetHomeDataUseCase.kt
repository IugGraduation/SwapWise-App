package com.sam.domain.home

import com.sam.data.repository.HomeRepository
import com.sam.domain.exception.EmptyDataException
import com.sam.domain.model.Home
import com.sam.domain.model.toHome
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Home {
        return homeRepository.getHomeDto()?.toHome() ?: throw EmptyDataException()
    }
}
