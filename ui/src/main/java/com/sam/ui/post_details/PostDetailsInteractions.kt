package com.sam.ui.post_details

import com.sam.ui.base.INavigateUp

interface PostDetailsInteractions : INavigateUp {
    fun navigateToEditPost(postId: String)
    fun onClickPhoneButton()
    fun onClickWhatsappButton()
    fun onClickMessageButton()
    fun onClickRetry()
}