package attachments

/**
 * Аудиозапись
 */
data class Audio(
    val id: Int, // Идентификатор аудиозаписи.
    val ownerId: Int, // Идентификатор владельца аудиозаписи.
    val artist: String, // Исполнитель.
    val title: String, // Название композиции.
    val duration: Int, // Длительность аудиозаписи в секундах.
    val url: String, // Ссылка на mp3.
    val lyricsId: Int, // Идентификатор текста аудиозаписи (если доступно).
    val albumId: Int, // Идентификатор альбома, в котором находится аудиозапись (если присвоен).
    val genreId: Int, // Идентификатор жанра из списка аудио жанров.
    val date: Int, // Дата добавления.
    val noSearch: Int = 0, // 1, если включена опция «Не выводить при поиске». Если опция отключена, поле не возвращается.
    val isHq: Int = 0 // 1, если аудио в высоком качестве.
)

/**
 * Аудиосообщение
 */
data class AudioMessage(
    override val type: String = "audioMessage",
    val duration: Int, // длительность аудиосообщения в секундах;
    val waveform: Array<Int>, // массив значений (integer) для визуального отображения звука;
    val linkOgg: String, // URL .ogg-файла;
    val linkMp3: String // URL .mp3-файла.
) : Preview

/**
 * Вид вложений - Аудиозапись
 */
class AttachmentAudio(val audio: Audio) : Attachment("audio")