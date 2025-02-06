package com.example.ui.home

import com.example.domain.GetHomeDataUseCase
import com.example.domain.model.Home
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(getHomeDataUseCase: GetHomeDataUseCase) :
    BaseViewModel<HomeUiState>(HomeUiState()), IHomeInteractions {

    init {
        tryToExecute(
            call = { getHomeDataUseCase() },
            onSuccess = ::onGetHomeDataSuccess,
        )
    }

    private fun onGetHomeDataSuccess(data: Home) {
        _state.value = HomeUiState.fromHome(data)
    }


    override fun onNewPostFieldChange(newValue: String) {
        _state.update { it.copy(newPost = newValue) }
    }

}