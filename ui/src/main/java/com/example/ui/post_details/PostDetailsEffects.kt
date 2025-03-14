package com.example.ui.post_details


sealed class PostDetailsEffects {
    data object NavigateToAddOffer : PostDetailsEffects()
    data class NavigateToOfferDetails(val offerId: String) : PostDetailsEffects()
    data class NavigateToEditOffer(val offerId: String) : PostDetailsEffects()
    data object NavigateUp : PostDetailsEffects()
}



