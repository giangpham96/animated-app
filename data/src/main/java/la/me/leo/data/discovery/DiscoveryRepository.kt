package la.me.leo.data.discovery

import la.me.leo.data.discovery.ItemJson.VenueItemJson

object DiscoveryRepository {

    fun provideDiscoveryData() = convertJsonDiscoveryToDomainDiscovery(getDiscoveryJsonModel())

    // Separate the below logic to a converter if necessary
    private fun convertJsonDiscoveryToDomainDiscovery(json: DiscoveryJson) : Discovery {
        return Discovery(
            json.city,
            json.sections.map(::convertJsonSectionToDomainSection)
        )
    }
    private fun convertJsonSectionToDomainSection(json: SectionJson) : Section<*> {
        val header = json.title.let{ if (it.isNotBlank()) it else null }
        return Section(header, json.items.map(::convertJsonItemToDomainItem))
    }
    private fun convertJsonItemToDomainItem(json: ItemJson) : Item {
        return when (json) {
            is ItemJson.HeroItemJson -> {
                val (rightText1, rightText2, showRightBadge) = resolveBannerRightProps(json.price)
                Item.Hero(
                    image = json.image.url,
                    blurHash = json.image.blurhash,
                    video = json.video?.url?.let{ if (it.isNotBlank()) it else null },
                    leftText1 = json.category,
                    leftText2 = json.title,
                    leftText3 = json.description,
                    rightText1 = rightText1,
                    rightText2 = rightText2,
                    showRightBadge = showRightBadge,
                )
            }
            is ItemJson.VenueItemJson -> {
                val overlayText = json.overlay?.let{ if (it.isNotBlank()) it else null }
                val srcVenue = json.venue
                val priceRange = formatPriceRange(srcVenue.priceRange, srcVenue.currency)
                val estimate = json.venue.estimate?.takeIf { overlayText == null }?.toString()
                val details = if (estimate != null) {
                    "$priceRange  ·  $estimate min"
                } else {
                    priceRange
                }
                Item.Venue(
                    image = json.image.url,
                    blurHash = json.image.blurhash,
                    overlayText = overlayText,
                    name = srcVenue.name,
                    tags = srcVenue.tags.orEmpty().take(2).joinToString(),
                    desc = srcVenue.shortDescription,
                    details = details,
                    rating5 = srcVenue.rating?.rating,
                    rating10 = srcVenue.rating?.score
                )
            }
            is ItemJson.MediumItemJson -> {
                val (rightText1, rightText2, showRightBadge) = resolveBannerRightProps(json.price)
                Item.Medium(
                    image = json.image.url,
                    blurHash = json.image.blurhash,
                    leftText1 = json.title,
                    leftText2 = json.description,
                    rightText1 = rightText1,
                    rightText2 = rightText2,
                    showRightBadge = showRightBadge
                )
            }
            is ItemJson.SquareTitleBottomItemJson -> {
                Item.SquareTitleBottom(
                    image = json.image.url,
                    blurHash = json.image.blurhash,
                    title = json.title,
                    desc = json.quantityString
                )
            }
        }
    }

    fun formatPriceRange(range: Int, currency: String?): String {
        val c = when (currency) {
            "EUR" -> "€"
            "JPY" -> "¥"
            else -> "$"
        }
        return c.repeat(range)
    }

    private fun resolveBannerRightProps(srcPrice: PriceJson?): Triple<String?, String?, Boolean> {
        var rightText1: String? = null
        var rightText2: String? = null
        var showRightBadge = false
        when (srcPrice) {
            is PriceJson.AbsoluteDiscountJson -> {
                rightText1 = srcPrice.current
                rightText2 = srcPrice.original
                showRightBadge = true
            }
            is PriceJson.RelativeDiscount -> {
                rightText1 = srcPrice.discountPercentage
                showRightBadge = true
            }
        }
        return Triple(rightText1, rightText2, showRightBadge)
    }
}
