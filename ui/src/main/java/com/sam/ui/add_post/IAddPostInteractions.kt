package com.sam.ui.add_post

import android.net.Uri
import com.sam.domain.model.LocationItem
import com.sam.ui.base.INavigateUp

interface IAddPostInteractions : INavigateUp {
    fun onTitleChange (title: String)
    fun onLocationChange(location: LocationItem)
    fun onDetailsChange(details: String)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickAdd(imageByteArray: ByteArray? = null)
    fun onRetryLocations()
    fun onRetryCategories()
}