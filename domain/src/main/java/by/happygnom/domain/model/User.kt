package by.happygnom.domain.model

import java.util.*

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val nickname: String?,
    val sex: Int,
    val startDate: Date,
    val pictureUrl: String?
)
