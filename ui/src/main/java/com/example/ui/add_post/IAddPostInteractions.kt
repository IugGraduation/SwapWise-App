package com.example.ui.add_post

import android.net.Uri
import com.example.ui.base.INavigateUp

interface IAddPostInteractions : INavigateUp {
    fun onTitleChange (title: String)
    fun onPlaceChange(place: String)
    fun onDetailsChange(details: String)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickAdd()
}