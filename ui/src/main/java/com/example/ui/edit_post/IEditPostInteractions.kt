package com.example.ui.edit_post

import android.net.Uri
import com.example.domain.model.LocationItem
import com.example.ui.base.INavigateUp

interface IEditPostInteractions : INavigateUp {
    fun onTitleChange(title: String)
    fun onLocationChange(location: LocationItem)
    fun onDetailsChange(details: String)
    fun onIsOpenChange (isOpen: Boolean)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickSave(imageByteArray: ByteArray?)
    fun onClickDelete()
}