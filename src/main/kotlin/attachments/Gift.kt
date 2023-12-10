package attachments
/**
 * Вид вложений - Подарок
 */
class AttachmentGift(override val type: String, val gift: Gift) : Attachment {
    override fun getAttachment(): Gift {
        return gift
    }
}

/**
 * Подарок
 */
data class Gift(
    val id: Int, // Идентификатор подарка.
    val thumb_256: String, // URL изображения 256x256px.
    val thumb_96: String, // URL изображения 96x96px.
    val thumb_48: String // URL изображения 48x48px.
) {
}