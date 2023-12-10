package attachments

/**
 * Аудиозапись
 */
data class Audio(
    val id: Int, // Идентификатор аудиозаписи.
    val owner_id: Int, // Идентификатор владельца аудиозаписи.
    val artist: String, // Исполнитель.
    val title: String, // Название композиции.
    val duration: Int, // Длительность аудиозаписи в секундах.
    val url: String, // Ссылка на mp3.
    val lyrics_id: Int, // Идентификатор текста аудиозаписи (если доступно).
    val album_id: Int, // Идентификатор альбома, в котором находится аудиозапись (если присвоен).
    val genre_id: Int, // Идентификатор жанра из списка аудио жанров.
    val date: Int, // Дата добавления.
    val no_search: Int = 0, // 1, если включена опция «Не выводить при поиске». Если опция отключена, поле не возвращается.
    val is_hq: Int = 0 // 1, если аудио в высоком качестве.
)

/**
 * Вид вложений - Аудиозапись
 */
class AttachmentAudio(override val type: String = "audio", val audio: Audio) : Attachment {
    override fun getAttachment(): Any {
        return audio
    }
}