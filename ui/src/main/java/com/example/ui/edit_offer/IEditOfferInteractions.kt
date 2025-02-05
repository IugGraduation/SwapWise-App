package com.example.ui.edit_offer

import android.net.Uri

interface IEditOfferInteractions {
    fun onTitleChange (title: String)
    fun onPlaceChange(place: String)
    fun onDetailsChange(details: String)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickSave()
    fun onClickDelete()
}