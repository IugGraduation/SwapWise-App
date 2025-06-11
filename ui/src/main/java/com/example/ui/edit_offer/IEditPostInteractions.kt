package com.example.ui.edit_offer

import android.net.Uri
import com.example.ui.base.INavigateUp

interface IEditPostInteractions : INavigateUp {
    fun onTitleChange (title: String)
    fun onPlaceChange(place: String)
    fun onDetailsChange(details: String)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickSave(imageByteArray: ByteArray? = null)
    fun onClickDelete()
}