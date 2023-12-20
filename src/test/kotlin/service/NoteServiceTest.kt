package service

import exception.CantCommentNoteException
import exception.NoteAccessDeniedException
import exception.NoteNotFoundException
import objects.Comment
import objects.Note
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class NoteServiceTest {

    @BeforeEach
    fun nsClear() {
        NoteService.clear()
    }

    /**
     * Успешный поиск заметки
     *  одновременно проверяется доступность заметки Аффтору - happy-pass
     */
    @Test
    fun getByIdFound() {
        val noteId = NoteService.add("title", "text", 0, 0, "", "", 99u)
        val note = NoteService.getById(noteId, 99u)

        assertEquals("text", note.text)
    }

    /**
     * 180 Note not found
     */
    @Test
    fun getByIdNotFound() {
        var noteId = NoteService.add("title", "text", 0, 0, "", "")

        assertThrows<NoteNotFoundException> {
            NoteService.getById(++noteId)
        }
    }

    /**
     * 181 Access to note denied
     *   доступ не открыт для всех
     */
    @Test
    fun getByIdNotAccess() {
        val noteId = NoteService.add("title", "text", 0, 0, "", "", 98u)

        assertThrows<NoteAccessDeniedException> {
            NoteService.getById(noteId, 99u)
        }
    }

    /**
     * 181 Access to note denied
     *   доступ открыт для всех
     */
    @Test
    fun getByIdWithAccess() {
        val noteId = NoteService.add("title", "text", 0, 0, "all", "", 98u)

        assertDoesNotThrow {
            NoteService.getById(noteId, 99u)
        }
    }

    /**
     * Создание заметки
     */
    @Test
    fun addNote() {
        val noteId = NoteService.add("title", "text", 0, 0, "all", "", 98u)
        val note: Note = NoteService.getById(noteId)

        val expected = Note(noteId, "title", "text", note.date, "all", "", ownerId = 98u)

        assertEquals(expected, note)
    }

    /**
     * Удаление Заметки - успех
     */
    @Test
    fun deleteNote() {
        val noteId = NoteService.add("title", "text", 0, 0, "all", "", 98u)
        val beforeDelete: Note = NoteService.getById(noteId)
        assertEquals(Note(noteId, "title", "text", beforeDelete.date, "all", "", ownerId = 98u), beforeDelete)
        assertEquals(NoteService.stateOpen, beforeDelete.state)

        NoteService.delete(noteId.toString())
        val afterDelete: Note = NoteService.getById(noteId)

        assertEquals(NoteService.stateDelete, afterDelete.state)
    }

    /**
     * Удаление Заметки - Заметка не найдена
     */
    @Test
    fun deleteNoteNotFound() {
        NoteService.add("title", "text", 0, 0, "all", "", 98u)

        assertThrows<NoteNotFoundException> {
            NoteService.delete("10")
        }
    }

    /**
     * Создание комментария
     */
    @Test
    fun createComment() {
        val service = NoteService
        val noteId = service.add("title", "text1", 0, 0, "", "", 1u)
        val commentId = service.createComment(noteId.toString(), "message", 1u)
        val comment = service.getComments(noteId)
        val expected =
            Comment(commentId, comment.first().fromId, comment.first().date, "message", replyToComment = noteId)

        assertEquals(expected, comment.first())
    }

    /**
     * Контроль создания повторно одного и того же комментария
     */
    @Test
    fun createCommentCant() {

        val service = NoteService
        val noteId = service.add("title", "text1", 0, 0, "", "", 1u)
        val guid = UUID.randomUUID().toString()
        var commentId = service.createComment(noteId.toString(), "message", 1u, guid = guid)

        assertThrows<CantCommentNoteException> {
            commentId = service.createComment(noteId.toString(), "message", 1u, guid = guid)
        }
    }

    /**
     * Не хватает прав для оставления Комментария под Заметкой
     */
    @Test
    fun createCommentAccessDenied() {

        val service = NoteService
        val noteId = service.add("title", "text1", 0, 0, "", "", 2u)

        assertThrows<NoteAccessDeniedException> {
            val commentId = service.createComment(noteId.toString(), "message", 1u)
        }
    }

    /**
     * Создание комментария под Заметкой, у которой стоит признак - разрешено оставлять комментарии всем
     */
    @Test
    fun createCommentAccessForAll() {

        val service = NoteService
        val noteId = service.add("title", "text1", 0, 0, "", "all", 2u)
        val commentId = service.createComment(noteId.toString(), "message", 1u)
        val comment = service.getComments(noteId)
        val expected =
            Comment(commentId, comment.first().fromId, comment.first().date, "message", replyToComment = noteId)

        assertEquals(expected, comment.first())
    }

    /**
     * Создание комментария под своей Заметкой
     */
    @Test
    fun createCommentAccessForSelf() {

        val service = NoteService
        val noteId = service.add("title", "text1", 0, 0, "", "", 1u)
        val commentId = service.createComment(noteId.toString(), "message", 1u)
        val comment = service.getComments(noteId)
        val expected =
            Comment(commentId, comment.first().fromId, comment.first().date, "message", replyToComment = noteId)

        assertEquals(expected, comment.first())
    }

    /**
     * Не найдена Заметка при попытке создания Комментария
     */
    @Test
    fun createCommentNotFount() {

        val service = NoteService
        var noteId = service.add("title", "text1", 0, 0, "", "")

        assertThrows<NoteNotFoundException> {
            val commentId = service.createComment((++noteId).toString(), "message")
        }
    }

    /**
     * Восстановление удаленного комментария
     */
    @Test
    fun restoreComment() {

        val service = NoteService
        val ownerId = 1u
        var noteId = service.add("title", "text1", 0, 0, "", "", ownerId)
        val commentId = service.createComment(noteId.toString(), "message", ownerId)

        assertEquals(service.stateOpen, service.getComments(noteId, ownerId).first().state)

        service.deleteComment(commentId, ownerId)

        assertEquals(service.stateDelete, service.getComments(noteId, ownerId).first().state)

        service.restoreComment(commentId, ownerId)

        assertEquals(service.stateOpen, service.getComments(noteId, ownerId).first().state)
    }

    /**
     * Нет прав для восстановления (права на просмотр) комментариев под Заметкой
     */
    @Test
    fun restoreCommentAccessDeniedView() {

        val service = NoteService
        val ownerId = 1u
        var noteId = service.add("title", "text1", 0, 0, "all", "", ownerId)
        val commentId = service.createComment(noteId.toString(), "message", ownerId)
        service.deleteComment(commentId, ownerId)

        assertThrows<NoteAccessDeniedException> {
            service.restoreComment(commentId, 2u)
        }
    }

    /**
     * Нет прав для восстановления (права на комментирование) комментариев под Заметкой
     */
    @Test
    fun restoreCommentAccessDeniedComment() {

        val service = NoteService
        val ownerId = 1u
        var noteId = service.add("title", "text1", 0, 0, "", "all", ownerId)
        val commentId = service.createComment(noteId.toString(), "message", ownerId)
        service.deleteComment(commentId, ownerId)

        assertThrows<NoteAccessDeniedException> {
            service.restoreComment(commentId, 2u)
        }
    }

    /**
     * Восстановление собственного комментария
     */
    @Test
    fun restoreCommentBySelf() {

        val service = NoteService
        val ownerId = 1u
        var noteId = service.add("title", "text1", 0, 0, "", "", ownerId)
        val commentId = service.createComment(noteId.toString(), "message", ownerId)
        service.deleteComment(commentId, ownerId)

        assertDoesNotThrow {
            service.restoreComment(commentId, ownerId)
        }
    }
    /**
     * Восстановление чужого комментария при наличии прав
     */
    @Test
    fun restoreCommentByAll() {

        val service = NoteService
        val ownerId = 1u
        var noteId = service.add("title", "text1", 0, 0, "all", "all", ownerId)
        val commentId = service.createComment(noteId.toString(), "message", ownerId)
        service.deleteComment(commentId, ownerId)

        assertDoesNotThrow {
            service.restoreComment(commentId, 2u)
        }
    }

    /**
     * Заметка не найдена
     */
    @Test
    fun restoreCommentNotFound() {

        val service = NoteService
        val ownerId = 1u
        var noteId = service.add("title", "text1", 0, 0, "all", "all", ownerId)
        val commentId = service.createComment(noteId.toString(), "message", ownerId)
        service.deleteComment(commentId, ownerId)
        service.delete(noteId.toString())

        assertThrows<NoteNotFoundException> {
            service.restoreComment(commentId, ownerId)
        }
    }
}