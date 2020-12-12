package la.me.leo.data.delivery

data class Delivery(val title: String, val items: List<Venue>)

data class Venue(
    val image: String,
    val blurHash: String?,
    val badges: List<String>,
    val overlayText: String?,
    val name: String,
    val desc: String?,
    val details: String,
    val rating5: Int?,
    val rating10: Float?,
    val deliveryEstimate: String?,
    val favorite: Boolean
)
