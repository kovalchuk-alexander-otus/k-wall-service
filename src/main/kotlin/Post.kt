import attachments.Attachment

/**
 * Запись на стене
 */
data class Post(
    var toId: UInt, // Идентификатор владельца стены, на которой размещена запись. В версиях API ниже 5.7 это поле называется to_id
    val fromId: UInt, // Идентификатор автора записи (от чьего имени опубликована запись)
    var text: String, // Текст записи
    var comments: Array<Comment>? = null, // Информация о комментариях к записи
    var likes: Array<Like>? = emptyArray<Like>(), // Информация о лайках к записи
    var views: Array<View> = emptyArray<View>(), // Информация о просмотрах записи
    var attachments: Array<Attachment>? = null, // Массив объектов, соответствующих медиаресурсам, прикреплённым к записи: фотографиям, документам, видеофайлам и другим.
    val canPin: Boolean = false, // Информация о том, может ли текущий пользователь закрепить запись
    val canDelete: Boolean = false, // Информация о том, может ли текущий пользователь удалить запись
    val canEdit: Boolean = false, // Информация о том, может ли текущий пользователь редактировать запись
    var id: Int? = null // Идентификатор записи
)
