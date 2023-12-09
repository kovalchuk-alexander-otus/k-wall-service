object WallService {
    private var id: Int = 0
    private var posts: Array<Post> = emptyArray<Post>()

    /**
     * Восемь бед - один reset (очистка собственных структур данных синглтона)
     */
    fun clear(){
        id = 0;
        posts = emptyArray();
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
}