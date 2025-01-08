package com.example.ui.edit_post

import android.net.Uri

interface IEditPostInteractions {
    fun onTitleChange (title: String)
    fun onPlaceChange(place: String)
    fun onDetailsChange(details: String)
    fun onIsOpenChange (isOpen: Boolean)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickSave()
    fun onClickDelete()
}