package com.example.ui.post_details

sealed interface PostDetailsEffects {
    data object NavigateToAddOffer : PostDetailsEffects
    data class NavigateToOfferDetails(val offerId: String) : PostDetailsEffects
    data class NavigateToEditOffer(val offerId: String) : PostDetailsEffects
    data class NavigateToEditPost(val postId: String) : PostDetailsEffects
    data object NavigateUp : PostDetailsEffects
    data object NavigateToPhone : PostDetailsEffects
    data object NavigateToWhatsapp : PostDetailsEffects
    data object NavigateToMessages : PostDetailsEffects
}
