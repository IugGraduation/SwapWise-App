package com.example.ui.add_post

import android.net.Uri

interface IAddPostInteractions {
    fun onTitleChange (title: String)
    fun onPlaceChange(place: String)
    fun onDetailsChange(details: String)
    fun onSelectedImageChange(selectedImageUri: Uri)
    fun onClickAddPost()
}