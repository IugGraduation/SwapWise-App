package com.example.data.model

import java.time.LocalDate


data class NotificationDto(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val date: LocalDate

)
