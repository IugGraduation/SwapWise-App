package com.example.ui.offer_details


sealed class OfferDetailsEffects {
    data object NavigateToPhone : OfferDetailsEffects()
    data object NavigateToWhatsapp : OfferDetailsEffects()
    data object NavigateToMessages : OfferDetailsEffects()
    data object NavigateUp : OfferDetailsEffects()
}



