package by.happygnom.domain.data_interface.model

interface UpdateNewsRequest {
    val newsId: Long
    val header: String
    val text: String
    val publishTimestamp: Long
    val pictureUrl: String?
    val pictureBase64: String?
}
