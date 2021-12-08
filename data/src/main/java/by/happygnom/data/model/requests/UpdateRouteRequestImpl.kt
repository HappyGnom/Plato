package by.happygnom.data.model.requests

import by.happygnom.domain.data_interface.model.UpdateRouteRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRouteRequestImpl(
    @SerialName("routeId")
    override val routeId: Long,
    @SerialName("grade")
    override val gradeLevel: Int,
    @SerialName("color")
    override val holdsColor: String,
    @SerialName("installDateTimestamp")
    override val setDateTimestamp: Long,
    @SerialName("setter")
    override val setterName: String?,
    @SerialName("pictureUrl")
    override val pictureUrl: String?,
    @SerialName("pictureBase64")
    override val pictureBase64: String?,
    @SerialName("tagIds")
    override val tagIds: List<Long>?,
) : UpdateRouteRequest
