package service

import exception.NoteAccessDeniedException
import exception.NoteNotFoundException
import objects.Note

object NoteService {

    private var id: UInt = 0u;
    private var notes: List<Note> = ArrayList();

    /**
     * Создает новую заметку у текущего пользователя.
     */
    fun add(
        title: String, // *Заголовок заметки.
        text: String, // *Текст заметки.
        privacy: Int? = 0, // Уровень доступа к заметке.
        /*
        Возможные значения:
            0 — все пользователи,
            1 — только друзья,
            2 — друзья и друзья друзей,
            3 — только пользователь.
         */
        commentPrivacy: Int? = 0, // Уровень доступа к комментированию заметки.
        /*
        Возможные значения:
            0 — все пользователи,
            1 — только друзья,
            2 — друзья и друзья друзей,
            3 — только пользователь.
         */
        privacyView: String? = "all", // Настройки приватности просмотра заметки в специальном формате.
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
            privacy_view: ['all', '-list2', -1234]
         */
        privacyComment: String? = "all", // Настройки приватности комментирования заметки в специальном формате.
        ownerId: UInt? = null // Аффтор жжёт
    ): UInt {
        val unixTime = System.currentTimeMillis()
        notes += Note(
            ++id,
            title,
            text,
            System.currentTimeMillis() / 1000,
            privacyView,
            commentPrivacy,
            ownerId = ownerId
        )
        return id
    }

    /**
     * Добавляет новый комментарий к заметке.
     */
    fun createComment() {

    }

    /**
     * Удаляет заметку текущего пользователя.
     */
    fun delete() {

    }

    /**
     * Удаляет комментарий к заметке.
     */
    fun deleteComment() {

    }

    /**
     * Редактирует заметку текущего пользователя.
     */
    fun edit() {

    }

    /**
     * Редактирует указанный комментарий у заметки.
     */
    fun editComment() {

    }

    /**
     * Возвращает список заметок, созданных пользователем.
     */
    fun get() {

    }

    /**
     * Возвращает заметку по её id.
     */
    fun getById(
        noteId: UInt, // *Идентификатор заметки.
        ownerId: UInt? = null, // Идентификатор владельца заметки.
        needWiki: Boolean? = false // Определяет, требуется ли в ответе wiki-представление заметки (работает, только если запрашиваются заметки текущего пользователя).
    ): Note {
        for (note in notes) {
            if (note.id == noteId) {
                if (note.ownerId == ownerId
                    || note.privacyView == "all"
                )
                    return note

                throw NoteAccessDeniedException("Access to note $noteId denied $ownerId.")
            }
        }
        throw NoteNotFoundException("Note not found $noteId.")

    }

    /**
     * Возвращает список комментариев к заметке.
     */
    fun getComments() {

    }

    /**
     * Возвращает список заметок друзей пользователя.
     */
    fun getFriendsNotes() {

    }

    /**
     * Восстанавливает удалённый комментарий.
     */
    fun restoreComment() {

    }

    fun clear() {
        id = 0u
        notes = ArrayList()
    }
}