package com.example.ui.profile

import com.example.domain.profile.GetCurrentMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserData: GetCurrentMemberUseCase,
)