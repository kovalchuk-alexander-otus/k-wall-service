package attachments

/**
 * Фотография
 *
 * Значения width и height могут быть недоступны для фотографий, загруженных на сайт до 2012 года.
 */
data class Photo(
    val id: Int, // Идентификатор фотографии
    val album_id: Int, // Идентификатор альбома, в котором находится фотография.
    val owner_id: Int, // Идентификатор владельца фотографии.
    val user_id: Int, // Идентификатор пользователя, загрузившего фото (если фотография размещена в сообществе). Для фотографий, размещенных от имени сообщества, user_id = 100.
    val text: String, // Текст описания фотографии.
    val date: Int, // Дата добавления в формате Unixtime.
    val thumb_hash: String, // Компактное представление заполнителя изображения. Можно использовать для показа во время загрузки реального изображения
    val sizes: Array<PhotoOptions>, // Массив с копиями изображения в разных размерах. Описание объекта находится на отдельной странице. Поле возвращается только при передаче параметра photo_sizes = 1 в запросе. Если параметр не передан, вместо поля sizes возвращаются поля, описанные ниже.
    val photo_75: String, // URL копии фотографии с максимальным размером 75x75px.
    val photo_130: String, // URL копии фотографии с максимальным размером 130x130px.
    val photo_604: String, // URL копии фотографии с максимальным размером 604x604px.
    val photo_807: String, // URL копии фотографии с максимальным размером 807x807px.
    val photo_1280: String, // URL копии фотографии с максимальным размером 1280x1024px.
    val photo_2560: String, // URL копии фотографии с максимальным размером 2560x2048px.
    val width: Int?, // Ширина оригинала фотографии в пикселах.
    val height: Int? // Высота оригинала фотографии в пикселах.
)

/**
 * Вид вложений - Фотография
 */
class AttachmentPhoto(override val type: String, val photo: Photo) : Attachment {
    override fun getAttachment(): Photo {
        return photo
    }
}

/**
 * Вид превью - Фотография
 */
class PreviewPhoto(
    override val type: String = "photo",
    val sizes: Array<PhotoOptions> // массив копий изображения в разных размерах.
) : Preview {
}

/**
 * Вид превью - Граффити
 */
class PreviewGraffiti(
    override val type: String = "graffiti",
    val graffiti: Graffiti
) : Preview

/**
 * Фотографии для превью
 */
data class PhotoOptions(
    val url: String, // URL копии изображения.
    val width: Int, // Ширина копии в пикселах.
    val height: Int, // Высота копии в пикселах.
    val type: String // Обозначение размера и пропорций копии.
)

/**
 * Граффити
 */
data class Graffiti(
    val src: String, // URL файла с граффити;
    val width: Int, // ширина изображения в px;
    val height: Int // высота изображения в px.
)
