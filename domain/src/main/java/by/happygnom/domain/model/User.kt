package by.happygnom.domain.model

import java.util.*

data class User(
    val idToken: String,
    val name: String,
    val surname: String,
    val nickname: String?,
    val sex: Int,
    val startDate: Long,
    val pictureUrl: String?
)