package com.example.data.model.response.profile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("item")
    val item: Item? = null
)