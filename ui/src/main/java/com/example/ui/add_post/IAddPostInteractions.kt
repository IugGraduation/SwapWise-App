package com.example.ui.add_post

import android.net.Uri
import com.example.domain.model.LocationItem
import com.example.ui.base.INavigateUp

interface IAddPostInteractions : INavigateUp {
    fun onTitleChange (title: String)
    fun onLocationChange(location: LocationItem)
    fun onDetailsChange(details: String)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickAdd(imageByteArray: ByteArray? = null)
    fun onRetryLocations()
}