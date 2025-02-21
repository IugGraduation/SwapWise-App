package com.example.ui.home

import com.example.domain.GetHomeDataUseCase
import com.example.domain.model.Home
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
        _state.value = MyUiState(HomeUiState.fromHome(data))
    }


    override fun onNewPostFieldChange(newValue: String) {
        updateData {
            copy(newPost = newValue)
        }
    }

}