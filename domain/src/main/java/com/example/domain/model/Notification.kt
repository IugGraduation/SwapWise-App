package com.example.domain.model

import java.time.LocalDate


data class Notification(
    val id: String = "",
    val userImage: String = "",
    val message: String = "",
    val date: LocalDate

)
