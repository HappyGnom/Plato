package by.happygnom.domain.model

data class User (
    val idToken: String,
    val name: String,
    val surname: String,
    val nickname: String?,
    val sex: String,
    val email: String,
    val pictureUrl: String?
)