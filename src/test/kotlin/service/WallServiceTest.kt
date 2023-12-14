package service

import exception.CommentNotFoundException
import exception.PostNotFoundException
import exception.WrongReasonException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import objects.Comment
import objects.Comments
import objects.Post
import kotlin.random.Random
import kotlin.random.nextUInt

class WallServiceTest {

    @BeforeEach
    fun wsClear() {
        WallService.clear()
    }

    @Test
    fun addExisting() {
        val before = Post(
            Random.nextUInt(),
            Random.nextUInt(),
            "Доброе утро, страна!",
            id = 1 // работаем с первым постом и поэтому id == 1
        )

        val after = WallService.add(
            before
        )

        assertEquals(before, after)
    }

    @Test
    fun updateGood() {

        WallService.add(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "Во-первых, выпить чашку воды!",
                arrayOf(Comments(1, true, true, true, true))
            )
        )
        WallService.add(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "Во-вторых, утренняя гигиена!",
                arrayOf(Comments())
            )
        )
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "В-третьих, утренняя зарядка и душ!"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "В-четвертых, завтрак!"))

        val result = WallService.update(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "В-четвертых, полезный завтрак!",
                arrayOf(Comments(3, false, false, false, true)),
                id = 4
            )
        )

        assertTrue(result)
    }

    @Test
    fun ubdateNotGood() {
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "дыва"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "тыри"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "читыре"))

        val result = WallService.update(Post(Random.nextUInt(), Random.nextUInt(), "пять", id = 5))

        assertFalse(result);
    }

    // Функция отрабатывает правильно, если добавляется комментарий к правильному посту.
    @Test
    fun addCommentToExistingPost() {
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "дыва"))

        val result = WallService.createComment(1, Comment(1u, 1u, 1, "cool"))

        assertEquals("cool", result.text)
    }

    // Функция выкидывает исключение, если была попытка добавить комментарий к несуществующему посту.
    @Test
    fun addCommentToNonExistingPost() {
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "дыва"))

        assertThrows<PostNotFoundException> {
            val result = WallService.createComment(9, Comment(1u, 1u, 1, "cool"))
        }
    }

    // Жалоба на комментарий
    @Test
    fun addReportToComment() {
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        val comment = WallService.createComment(1, Comment(1u, 11u, 1, "not bad"))
        val result = WallService.reportComment(11u, 1u, 4u)

        assertEquals(4u, result.reason)
    }

    // Ошибка при оставлении жалобы на комментарий с указанием неверной причины
    @Test
    fun addWrongReasonReportToComment() {
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        val comment = WallService.createComment(1, Comment(1u, 11u, 1, "not bad"))

        assertThrows<WrongReasonException> {
            val result = WallService.reportComment(11u, 1u, 9u)
        }
    }

    // Ошибка при оставлении жалобы на несуществующий комментарий (id комментария)
    @Test
    fun addReportToNonExistingComment() {
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        val comment = WallService.createComment(1, Comment(1u, 11u, 1, "not bad"))

        assertThrows<CommentNotFoundException> {
            val result = WallService.reportComment(11u, 2u, 8u)
        }
    }

    // Ошибка при оставлении жалобы на несуществующий комментарий (id автора)
    @Test
    fun addReportToOtherAuthorComment() {
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        val comment = WallService.createComment(1, Comment(1u, 11u, 1, "not bad"))

        assertThrows<CommentNotFoundException> {
            val result = WallService.reportComment(9u, 1u, 8u)
        }
    }
}