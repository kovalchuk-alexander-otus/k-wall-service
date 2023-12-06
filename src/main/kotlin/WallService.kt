class WallService {
    private var posts: Array<Post> = emptyArray<Post>()

    fun add(post: Post): Post{
        posts += post
        return posts.last()
    }
}