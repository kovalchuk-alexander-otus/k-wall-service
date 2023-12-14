package service

import objects.Comment
import objects.Post
import objects.Report
import exception.CommentNotFoundException
import exception.PostNotFoundException
import exception.WrongReasonException

/**
 * ..сие есть singleton - определяется словом object
 */
object WallService {
    private var id: Int = 0
    private var posts: Array<Post> = emptyArray<Post>()
    private var comments: Array<Comment> = emptyArray<Comment>()
    private var reports: Array<Report> = emptyArray<Report>()

    /**
     * Восемь бед - один reset (очистка собственных структур данных синглтона)
     */
    fun clear() {
        id = 0
        posts = emptyArray()
        comments = emptyArray()
        reports = emptyArray()
    }

    /**
     * Создание записи
     */
    fun add(post: Post): Post {
        posts += post.copy(id = ++id)
        return posts.last()
    }

    /**
     * Обновление записи
     */
    fun update(post: Post): Boolean {
        posts.forEachIndexed { i, p ->
            if (p.id == post.id) {
                posts[i] = post.copy()
                return true
            }
        }
        return false
    }

    /**
     * Пролистать все сообщения
     */
    fun showAll() {
        posts.forEach(::println)
    }

    /**
     * Создание комментария
     */
    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                comments += comment.copy()
                return comments.last()
            }
        }
        throw PostNotFoundException("Пост $postId, к которому пытаемся добавить комментарий $comment, не существует.")
    }

    /**
     * Жалоба о комментарии:
     *
     *     0 — спам;
     *     1 — детская порнография;
     *     2 — экстремизм;
     *     3 — насилие;
     *     4 — пропаганда наркотиков;
     *     5 — материал для взрослых;
     *     6 — оскорбление;
     *     8 — призывы к суициду.
     */
    fun reportComment(
        ownerId: UInt, // Идентификатор пользователя или сообщества, которому принадлежит комментарий.
        commentId: UInt, // Идентификатор комментария.
        reason: UInt // Причина жалобы:
    ): Report {
        if (reason !in 0u..6u && reason != 8u) throw WrongReasonException("Некорректное значение причины жалобы $reason.")

        for (comment in comments) {
            if (comment.id == commentId && comment.fromId == ownerId) {
                reports += Report(comment, reason)
                return reports.last()
            }
        }

        throw CommentNotFoundException("Не найден комментарий с id $commentId от пользователя $ownerId.")
    }
}

