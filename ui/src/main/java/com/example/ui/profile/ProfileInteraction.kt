package com.example.ui.profile

import android.content.Context
import android.net.Uri

interface ProfileInteraction {
    fun onUpdateProfileImage(imageUri: Uri)
}