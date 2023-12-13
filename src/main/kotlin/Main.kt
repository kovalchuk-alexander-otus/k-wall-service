import service.NoteService
import service.WallService

fun main() {
    val wall = WallService
    wall.showAll()

    val note = NoteService
    note.add("title", "text", 0, 0, "", "")
    note.add("title", "text")

    println(note.getById(1u))
    println(note.getById(2u))
}