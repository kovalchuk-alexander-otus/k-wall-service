package objects

import attachments.Attachment
import service.NoteService.stateOpen

/**
 * Комментарий на стене
 */
data class Comment(
    val id: UInt, // Идентификатор комментария
    val fromId: UInt?, // Идентификатор автора комментария
    val date: Long, // Дата создания комментария в формате Unixtime
    var text: String, // Текст комментария
    val donut: Donut? = null, // Информация о VK Donut
    val replyToUser: UInt? = null, // Идентификатор пользователя или сообщества, в ответ которому оставлен текущий комментарий (если применимо)
    val replyToComment: UInt? = null, // Идентификатор комментария, в ответ на который оставлен текущий (если применимо)
    val attachments: Array<Attachment>? = null, // Медиавложения комментария (фотографии, ссылки и т.п.)
    val parentsStack: Array<Comment>? = null, // Массив идентификаторов родительских комментариев
    val thread: Thread? = null, // Информация о вложенной ветке комментариев
    var state: UInt? = stateOpen // Статус заметки: 0 - удалена, 1 - создана
)

/**
 * Признак донатера
 */
data class Donut(
    val isDon: Boolean, // является ли комментатор подписчиком VK Donut.
    val placeholder: String // заглушка для пользователей, которые не оформили подписку VK Donut.
)

/**
 * Ветка комментариев
 */
data class Thread(
    val count: Int, // количество комментариев в ветке
    val items: Array<Comment>, // массив объектов комментариев к записи (только для метода wall.getComments)
    val canPost: Boolean, // может ли текущий пользователь оставлять комментарии в этой ветке
    val showReplyButton: Boolean, // нужно ли отображать кнопку «ответить» в ветке
    val groupsCanPost: Boolean // могут ли сообщества оставлять комментарии в ветке
)

/**
 * Жалобы на комментарии
 */
data class Report(
    val comment: Comment,
    val reason: UInt
)