package com.sam.ui.splash

import androidx.lifecycle.viewModelScope
import com.sam.domain.authentication.CheckAuthUseCase
import com.sam.domain.exception.EmptyDataException
import com.sam.domain.exception.InactiveAccountException
import com.sam.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase,
) : BaseViewModel<Boolean, SplashEffects>(false) {

    init {
        viewModelScope.launch { delay(1000) }
        checkAuthState()
    }


    private fun checkAuthState() {
        tryToExecute(
            call = { checkAuthUseCase() },
            onSuccess = { navigateToHome() },
            onError = ::onCheckAuthFail,
        )
    }

    private fun navigateToHome() {
        sendUiEffect(SplashEffects.NavigateToHome)
    }

    private fun onCheckAuthFail(throwable: Throwable) {
        when (throwable) {
            is EmptyDataException -> {
                navigateToLogin()
            }

            is InactiveAccountException -> {
                navigateToOtp()
            }

            else -> onActionFail(throwable)
        }
    }

    private fun navigateToLogin() {
        sendUiEffect(SplashEffects.NavigateToLogin)
    }

    private fun navigateToOtp() {
        sendUiEffect(SplashEffects.NavigateToOtp)
    }

}

