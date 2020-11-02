package la.me.leo.data.discovery

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

internal fun getDiscoveryJsonModel() : DiscoveryJson {
    val format = Json {
        classDiscriminator = "template"
        ignoreUnknownKeys = true
    }
    return format.decodeFromString(DiscoveryJson.serializer(), DISCOVERY_DATA)
}

@Serializable
internal class DiscoveryJson(
    val city: String,
    val sections: List<SectionJson>
)

@Serializable
internal class SectionJson(
    val items: List<ItemJson>,
    val name: String,
    val template: String,
    val title: String
)

@Serializable
internal sealed class ItemJson {
    abstract val title: String
    abstract val type: String
    abstract val image: ImageJson

    @Serializable
    @SerialName("hero")
    internal class HeroItemJson(
        override val image: ImageJson,
        override val title: String,
        override val type: String,
        val description: String? = null,
        val category: String? = null,
        val video: VideoJson? = null,
        val price: PriceJson? = null
    ) : ItemJson()

    @Serializable
    @SerialName("venue")
    internal class VenueItemJson(
        override val image: ImageJson,
        override val title: String,
        override val type: String,
        val overlay: String? = null,
        val venue: VenueJson
    ) : ItemJson()

    @Serializable
    @SerialName("medium")
    internal class MediumItemJson(
        override val image: ImageJson,
        override val title: String,
        override val type: String,
        val description: String,
        val price: PriceJson? = null,
        val category: String? = null
    ) : ItemJson()

    @Serializable
    @SerialName("square-title-bottom")
    internal class SquareTitleBottomItemJson(
        override val image: ImageJson,
        override val title: String,
        override val type: String,
        val quantity: Int,
        @SerialName("quantity_str")
        val quantityString: String
    ) : ItemJson()
}

@Serializable
internal class ImageJson(
    val blurhash: String? = null,
    val url: String
)

@Serializable
internal class VideoJson(
    val blurhash: String? = null,
    val url: String
)

@Serializable
internal sealed class PriceJson {
    @Serializable
    @SerialName("absolute-discount")
    internal class AbsoluteDiscountJson(
        val current: String,
        val original: String
    ) : PriceJson()

    @Serializable
    @SerialName("relative-discount")
    internal class RelativeDiscount(
        @SerialName("discount_percentage")
        val discountPercentage: String
    ) : PriceJson()
}

@Serializable
internal class VenueJson(
    val address: String,
    val badges: List<BadgeJson>,
    val city: String,
    val currency: String,
    val delivers: Boolean,
    @SerialName("delivery_price")
    val deliveryPrice: String,
    val estimate: Int,
    val favourite: Boolean,
    val franchise: String,
    val location: List<Double>? = null,
    val rating: RatingJson? = null,
    val name: String,
    val online: Boolean,
    @SerialName("price_range")
    val priceRange: Int,
    @SerialName("product_line")
    val productLine: String,
    @SerialName("short_description")
    val shortDescription: String? = null,
    val tags: List<String>? = null
)

@Serializable
internal class BadgeJson(
    val text: String,
    val variant: String
)

@Serializable
internal class RatingJson(
    val rating: Int,
    val score: Float
)

