package la.me.leo.data.delivery

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import la.me.leo.data.discovery.ImageJson
import la.me.leo.data.discovery.VenueJson

@Serializable
internal class VenueItemJson(
    val image: ImageJson,
    val title: String,
    val overlay: String? = null,
    val venue: VenueJson
)

@Serializable
internal class DeliveryJson(
    val items: List<VenueItemJson>,
    @SerialName("page_title") val pageTitle: String
)
