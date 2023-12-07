class WallService {
    private var id: Int = 0
    private var posts: Array<Post> = emptyArray<Post>()

    /**
     * Создание записи
     *
     * добавлять запись в массив, но при этом назначать посту уникальный среди всех постов идентификатор;
     * возвращать пост с уже выставленным идентификатором.
     */
    fun add(post: Post): Post {
        post.id = ++id;
        posts += post
        return posts.last()
    }

    /**
     * Обновление записи
     */
    fun update(post: Post): Boolean {
        posts.forEachIndexed { i, p ->
            if (p.id == post.id) {
                posts[i] = p.copy(
                    toId = post.toId,
                    fromId = post.fromId,
                    text = post.text,
                    comments = post.comments,
                    likes = post.likes,
                    views = post.views,
                    canPin = post.canPin,
                    canDelete = post.canDelete,
                    canEdit = post.canEdit
                )
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
}