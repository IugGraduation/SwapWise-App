package com.example.ui.edit_post

import com.example.ui.edit_offer.IEditOfferInteractions

interface IEditPostInteractions: IEditOfferInteractions {
    fun onIsOpenChange (isOpen: Boolean)
}