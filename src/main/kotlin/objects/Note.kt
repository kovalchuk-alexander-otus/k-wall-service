package objects

import service.NoteService.stateOpen

data class Note(
    val id: UInt, // Идентификатор заметки.
    var title: String, // *Заголовок заметки.
    var text: String, // *Текст заметки.
    var date: Long, // Дата создания заметки в формате Unixtime.
    var privacyView: String?, // Настройки приватности просмотра заметки.
    /*
    Приватность возвращается в API в виде массива из следующих возможных значений:
        all – Доступно всем пользователям;
        friends – Доступно друзьям текущего пользователя;
        friends_of_friends / friends_of_friends_only – Доступно друзьям и друзьям друзей / друзьям друзей текущего пользователя (friends_of_friends_only появился с версии 5.32);
        nobody / only_me – Недоступно никому / доступно только мне;
        list{list_id} – Доступно друзьям текущего пользователя из списка с идентификатором {list_id};
        {user_id} – Доступно другу с идентификатором {user_id};
        -list{list_id} – Недоступно друзьям текущего пользователя из списка с идентификатором {list_id};
        -{user_id} – Недоступно другу с идентификатором {user_id}.

    Пример:
        Доступно всем пользователям, кроме друзей из списка №2 и кроме друга id1234:
        privacyView: ['all', '-list2', -1234]
     */
    var privacyComment: String? = "all", // Настройки приватности комментирования заметки.
    /*
    TODO: наблюдается какое-то дублирование по назначению атрибутов Заметки - privacy и commentPrivacy
    Возможные значения:
        0 — все пользователи,
        1 — только друзья,
        2 — друзья и друзья друзей,
        3 — только пользователь.
     */
    val textWiki: String? = "", // Тэги ссылок на wiki.
    val ownerId: UInt? = 0u, // Идентификатор владельца заметки.
    val viewUrl: String? = "url://vk.ru", // URL страницы для отображения заметки.
    val comments: UInt? = 0u, // Количество комментариев.
    val readComments: UInt? = 0u, // Количество прочитанных комментариев (только при запросе информации о заметке текущего пользователя).
    var state: UInt? = stateOpen // Статус заметки: 0 - удалена, 1 - создана
)
