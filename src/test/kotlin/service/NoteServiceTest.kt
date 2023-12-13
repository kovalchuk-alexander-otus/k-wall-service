package service

import exception.NoteAccessDeniedException
import exception.NoteNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NoteServiceTest {

    @BeforeEach
    fun nsClear() {
        NoteService.clear()
    }

    /**
     * успешный поиск заметки
     *  одновременно проверяется доступность заметки Аффтору - happy-pass
     */
    @Test
    fun getByIdFound() {
        NoteService.add("title", "text", 0, 0, "", "", 99u)
        val note = NoteService.getById(1u, 99u)

        assertEquals("text", note.text)
    }

    /**
     * 180 Note not found
     */
    @Test
    fun getByIdNotFound() {
        NoteService.add("title", "text", 0, 0, "", "")

        assertThrows<NoteNotFoundException> {
            NoteService.getById(2u)
        }
    }

    /**
     * 181 Access to note denied
     *   доступ не открыт для всех
     */
    @Test
    fun getByIdNotAccess() {
        NoteService.add("title", "text", 0, 0, "", "", 98u)

        assertThrows<NoteAccessDeniedException> {
            NoteService.getById(1u, 99u)
        }
    }

    /**
     * 181 Access to note denied
     *   доступ открыт для всех
     */
    @Test
    fun getByIdWithAccess() {
        NoteService.add("title", "text", 0, 0, "all", "", 98u)

        assertDoesNotThrow {
            NoteService.getById(1u, 99u)
        }
    }

}