package by.happygnom.domain.model

import java.util.*

data class Comment(
    val id: Long,
    val message: String,
    val userName: String,
    val userPictureUrl: String?,
    val dateTime: Date
)
