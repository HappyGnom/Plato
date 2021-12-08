package by.happygnom.domain.data_interface.model

interface CreateRouteRequest {
    val gradeLevel: Int
    val holdsColor: String
    val setDateTimestamp: Long
    val setterName: String?
    val pictureUrl: String?
    val pictureBase64: String?
    val tagIds: List<Long>?
}
