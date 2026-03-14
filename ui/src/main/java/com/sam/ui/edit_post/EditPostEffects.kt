package com.sam.ui.edit_post

sealed class EditPostEffects {
    data object NavigateUp : EditPostEffects()
    data object NavigateToHome : EditPostEffects()
}
