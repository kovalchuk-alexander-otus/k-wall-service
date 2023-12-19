package service

import exception.CantCommentNoteException
import exception.NoteAccessDeniedException
import exception.NoteNotFoundException
import objects.Comment
import objects.Note
import java.util.UUID

object NoteService {

    private var noteId: UInt = 0u
    private var commentId: UInt = 0u
    private var notes: List<Note> = ArrayList()
    private var comments: MutableMap<String, Comment> = HashMap()
    
    val stateOpen : UInt = 1u
    val stateDelete : UInt = 0u

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
            ++noteId,
            title,
            text,
            System.currentTimeMillis(),
            privacyView,
            privacyComment,
            ownerId = ownerId
        )
        return noteId
    }

    /**
     * Добавляет новый комментарий к заметке.
     */
    fun createComment(
        noteId: String, // *Идентификатор заметки.
        message: String, // *Текст комментария.
        ownerId: UInt? = null, // Идентификатор владельца заметки.
        replyTo: UInt? = null, // Идентификатор пользователя, ответом на комментарий которого является добавляемый комментарий (не передаётся, если комментарий не является ответом).
        guid: String? = UUID.randomUUID()
            .toString() // Уникальный идентификатор, предназначенный для предотвращения повторной отправки одинакового комментария.
    ): UInt {
        if (comments.containsKey(guid))
            throw CantCommentNoteException("You can't comment this note $noteId.")
        java.lang.Thread.sleep(1) // чтобы сортировка по времени была очевидна - сделаем тут задержку в 1 сек
        for (note in notes) {
            if (note.id == noteId.toUInt()) {
                if (note.ownerId == ownerId
                    || note.privacyView == "all"
                ) {
                    comments.put(
                        guid ?: UUID.randomUUID().toString(),
                        Comment(
                            ++commentId,
                            ownerId,
                            System.currentTimeMillis(),
                            message,
                            replyToUser = replyTo,
                            replyToComment = noteId.toUInt()
                        )
                    )
                    return commentId
                }

                throw NoteAccessDeniedException("Access to note $noteId denied $ownerId.")
            }
        }
        throw NoteNotFoundException("Note not found $noteId.")
    }

    /**
     * Удаляет заметку текущего пользователя.
     */
    fun delete(
        noteId: String // *Идентификатор заметки.
    ): UInt {
        var result: UInt = 0u
        notes.stream().filter { n -> n.id == noteId.toUInt() }.forEach { n ->
            n.state = stateDelete
            result = 1u
        }

        if (result == 1u) return result else throw NoteNotFoundException("Note not found $noteId.")
    }

    /**
     * Удаляет комментарий к заметке.
     */
    fun deleteComment(
        commentId: UInt, // *Идентификатор комментария.
        ownerId: UInt // Идентификатор владельца заметки.
    ) = changeStateComment(commentId, ownerId, stateDelete)

    /**
     * TODO: Редактирует заметку текущего пользователя.
     */
    fun edit() {

    }

    /**
     * TODO: Редактирует указанный комментарий у заметки.
     */
    fun editComment() {

    }

    /**
     * TODO: Возвращает список заметок, созданных пользователем.
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
    fun getComments(
        noteId: UInt, // *Идентификатор заметки.
        ownerId: UInt? = null, // Идентификатор владельца заметки.
        sort: UInt = 0u, // Сортировка результатов (0 — по дате добавления в порядке возрастания, 1 — по дате добавления в порядке убывания).
        offset: UInt = 0u, // Смещение, необходимое для выборки определенного подмножества комментариев.
        count: UInt = 10u // Количество комментариев, которое необходимо получить.
    ): List<Comment> {
        var result =
            comments.filter { c -> c.value.replyToComment == noteId && c.value.fromId == ownerId ?: c.value.fromId }.values.toList()
        result = if (sort == 0u) result.sortedBy { s -> s.date } else result.sortedByDescending { s -> s.date }
        return result.subList(offset.toInt(), (offset + count).toInt())
    }

    /**
     * Возвращает список заметок друзей пользователя.
     *
     * Данный метод устарел и ~может быть~ отключён ~через некоторое время~, пожалуйста, избегайте его использования.
     */
    fun getFriendsNotes() {}

    /**
     * Восстанавливает удалённый комментарий.
     */
    fun restoreComment(
        commentId: UInt, // *Идентификатор удаленного комментария.
        ownerId: UInt // Идентификатор владельца заметки.
    ) = changeStateComment(commentId, ownerId, stateOpen)

    fun changeStateComment(
        commentId: UInt, // *Идентификатор удаленного комментария.
        ownerId: UInt, // Идентификатор владельца заметки.
        state: UInt // Статус
    ): UInt {
        var result: UInt = 0u
        comments.filter { (key, com) -> com.id == commentId }.forEach { (key, com) ->
            for (note in notes.stream().filter { note -> note.id == com.replyToComment }) {
                result = 1u // признак, что нашли
                if (note.ownerId != ownerId && note.privacyView != "all"
                ) {
                    throw NoteAccessDeniedException("Access to note $noteId denied $ownerId.")
                }
                if (note.ownerId != ownerId && note.privacyComment != "all"
                ) {
                    throw NoteAccessDeniedException("Access to comment $noteId denied $ownerId.")
                }
                com.state = state
            }
            if (result == 0u) throw NoteNotFoundException("Note not found $noteId.")
        }
        return result
    }

    /**
     * only-for-test
     */
    fun clear() {
        noteId = 0u
        commentId = 0u
        notes = ArrayList()
        comments = HashMap()
    }
}

