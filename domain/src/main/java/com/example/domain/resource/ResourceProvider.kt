package com.example.domain.resource

interface ResourceProvider {
    fun getString(resId: Int): String
}