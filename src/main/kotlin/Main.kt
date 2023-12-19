import service.NoteService
import service.WallService

fun main() {

    // CRUD (Create, Read, Update, Delete)
    // вероятно имеется ввиду, что методы должны быть оформлены с генериками

    val wall = WallService
    wall.showAll()

    val note = NoteService
    note.add("title", "text1", 0, 0, "", "")
    note.add("title", "text2")
    note.add("title", "text3")

    println(note.getById(1u))
    println(note.getById(2u))

    note.createComment("1", "message")
    note.createComment("3", "message1", 1u)
    note.createComment("3", "message2", 1u)
    note.createComment("3", "message3", 2u)
    note.createComment("3", "message4", 2u)
    note.createComment("3", "message5", 1u)
    note.createComment("3", "message6", 3u)

    note.getComments(3u, 1u, 1u, 0u, 3u).forEach { c -> println(c) }

    note.getFriendsNotes()

    println(note.delete("1"))
    // println(note.delete("5"))

    println(note.getById(1u))
    println(note.getById(2u))
}