package attachments

/**
 * Вложения
 */
interface Attachment {
    val type: String

    fun getAttachment(): Any
}