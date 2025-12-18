package com.example.ui.post_details

import com.example.ui.base.INavigateUp

interface PostDetailsInteractions : INavigateUp {
    fun navigateToEditPost(postId: String)
    fun onClickPhoneButton()
    fun onClickWhatsappButton()
    fun onClickMessageButton()
}