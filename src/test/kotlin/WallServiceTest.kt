import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
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
            "Доброе утро, страна!"
        )

        val after = WallService.add(
            before
        )

        assertEquals(before, after)
    }

    @Test
    fun updateExisting() {

        WallService.add(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "Во-первых, выпить чашку воды!",
                arrayOf(Comment(1, true, true, true, true))
            )
        )
        WallService.add(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "Во-вторых, утренняя гигиена!",
                arrayOf(Comment())
            )
        )
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "В-третьих, утренняя зарядка и душ!"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "В-четвертых, завтрак!"))

        val result = WallService.update(
            Post(
                Random.nextUInt(),
                Random.nextUInt(),
                "В-четвертых, полезный завтрак!",
                arrayOf(Comment(3, false, false, false, true)),
                id = 2
            )
        )

        assertTrue(result)
    }
}