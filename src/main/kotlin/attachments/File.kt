package attachments

class AttachmentFile(override val type: String = "file", val file: File) : Attachment {
    override fun getAttachment(): File {
        return file
    }
}

data class File(
    val id: Int, //
    val owner_id: Int, // Идентификатор пользователя, загрузившего файл.
    val title: String, // Название файла.
    val size: Int, // Размер файла в байтах.
    val ext: String, // Расширение файла.
    val url: String, // Адрес файла, по которому его можно загрузить.
    val date: Int, // Дата добавления в формате Unixtime.
    val type: Int, /* Тип файла. Возможные значения:
1 — текстовые документы;
2 — архивы;
3 — gif;
4 — изображения;
5 — аудио;
6 — видео;
7 — электронные книги;
8 — неизвестно.*/
    val preview: Preview // Информация для предварительного просмотра файла.
) {
}