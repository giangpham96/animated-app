package la.me.leo.data.delivery

import android.content.Context
import kotlinx.serialization.json.Json
import la.me.leo.data.R
import la.me.leo.data.readJsonFromAsset

class DeliveryRepository(private val context: Context) {
    fun provideDeliveryData() = convertDeliveryJsonToDeleveryDomain(getDeliveryJsonModel())

    private fun convertDeliveryJsonToDeleveryDomain(json: DeliveryJson): Delivery {
        return Delivery(
            json.pageTitle,
            json.items.map(::convertVenueJsonToVenueDomain)
        )
    }

    private fun convertVenueJsonToVenueDomain(src: VenueItemJson) : Venue {
        val priceRange = "€".repeat(src.venue.priceRange)
        val overlayText = src.overlay?.takeIf { it.isNotBlank() }
        val details = if (overlayText == null && !src.venue.deliveryPrice.isEmpty()) {
            "$priceRange  ·  ${context.getString(R.string.venues_list_delivery, src.venue.deliveryPrice)}"
        } else {
            priceRange
        }
        return Venue(
            image = src.image.url,
            blurHash = src.image.blurhash,
            overlayText = src.overlay?.takeIf { it.isNotBlank() },
            name = src.venue.name,
            desc = src.venue.shortDescription,
            details = details,
            rating5 = src.venue.rating?.rating,
            rating10 = src.venue.rating?.score,
            deliveryEstimate = src.venue.estimate.takeIf { overlayText == null }?.toString(),
            favorite = src.venue.favourite
        )
    }

    private fun getDeliveryJsonModel(): DeliveryJson {
        val format = Json {}
        val json = context.readJsonFromAsset("delivery.json")
        return format.decodeFromString(DeliveryJson.serializer(), json)
    }
}
