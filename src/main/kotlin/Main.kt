import kotlin.random.Random
import kotlin.random.nextUInt

fun main() {

    val post = Post(Random.nextUInt(), Random.nextUInt(), "Доброе утро, страна!");
    val wall = WallService()

    println(
        wall.add(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "Во-первых, выпить чашку воды!",
                arrayOf(Comment(1, true, true, true, true))
            )
        )
    )
    println(wall.add(Post(Random.nextUInt(), Random.nextUInt(), "Во-вторых, утренняя гигиена!", arrayOf(Comment()))))
    println(wall.add(Post(Random.nextUInt(), Random.nextUInt(), "В-третьих, утренняя зарядка и душ!")))
    println(wall.add(Post(Random.nextUInt(), Random.nextUInt(), "В-четвертых, завтрак!")))

    println(
        wall.update(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "В-четвертых, полезный завтрак!",
                arrayOf(Comment(3, false, false, false, true)),
                id = 2
            )
        )
    )
    println(wall.update(Post(Random.nextUInt(), Random.nextUInt(), "В-четвертых, полезный завтрак!", id = 4)))
    wall.showAll()

}