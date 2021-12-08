package by.happygnom.domain.model

import java.util.*

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val nickname: String?,
    val sex: Int,
    val startDate: Date,
    val pictureUrl: String?,
    val liked: List<Long>?,
    val sent: List<Long>?,
    val favorite: List<Long>?
)