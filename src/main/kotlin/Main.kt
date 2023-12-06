import kotlin.random.Random
import kotlin.random.nextUInt

fun main() {

    val post = Post(Random.nextUInt(), Random.nextUInt(), Random.nextUInt(), "Доброе утро, страна!");
    val wall = WallService()

    println(wall.add(post))
}