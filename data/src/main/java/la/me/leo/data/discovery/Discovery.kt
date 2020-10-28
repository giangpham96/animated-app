package la.me.leo.data.discovery

data class Discovery(val city: String, val sections: List<Section>)

data class Section(val header: String?, val items: List<Item>)

sealed class Item {

    abstract val image: String
    abstract val blurHash: String?

    data class Hero(
        override val image: String,
        override val blurHash: String?,
        val video: String?,
        val leftText1: String?,
        val leftText2: String?,
        val leftText3: String?,
        val rightText1: String?,
        val rightText2: String?,
        val showRightBadge: Boolean,
    ) : Item()

    data class Medium(
        override val image: String,
        override val blurHash: String?,
        val leftText1: String?,
        val leftText2: String?,
        val rightText1: String?,
        val rightText2: String?,
        val showRightBadge: Boolean
    ) : Item()

    data class SquareTitleBottom(
        override val image: String,
        override val blurHash: String?,
        val title: String,
        val desc: String?
    ) : Item()

    data class Venue(
        override val image: String,
        override val blurHash: String?,
        val overlayText: String?,
        val name: String,
        val tags: String,
        val desc: String?,
        val details: String,
        val rating5: Int?,
        val rating10: Float?
    ) : Item()

}
