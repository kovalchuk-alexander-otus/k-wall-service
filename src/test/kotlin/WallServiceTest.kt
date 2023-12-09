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
                id = 4
            )
        )

        assertTrue(result)
    }

    @Test
    fun ubdateNotGood(){
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "ррраз"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "дыва"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "тыри"))
        WallService.add(Post(Random.nextUInt(), Random.nextUInt(), "читыре"))

        val result = WallService.update(Post(Random.nextUInt(), Random.nextUInt(), "пять",  id = 5))

        assertFalse(result);
    }
}