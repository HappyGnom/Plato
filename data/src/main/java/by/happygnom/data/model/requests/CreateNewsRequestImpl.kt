package by.happygnom.data.model.requests

import by.happygnom.domain.data_interface.model.CreateNewsRequest
import by.happygnom.domain.data_interface.model.CreateRouteRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateNewsRequestImpl(
    @SerialName("header")
    override val header: String,
    @SerialName("text")
    override val text:String,
    @SerialName("publishTimestamp")
    override val publishTimestamp: Long,
    @SerialName("mainPictureUrl")
    override val pictureUrl: String?,
    @SerialName("mainPictureBase64")
    override val pictureBase64: String?
) : CreateNewsRequest
