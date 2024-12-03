package com.example.domain.resource

fun interface ResourceProvider {
    fun getString(resId: Int): String
}