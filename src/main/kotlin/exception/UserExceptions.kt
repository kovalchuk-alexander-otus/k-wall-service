package exception

/**
 * Post's
 */
class PostNotFoundException(message: String) : RuntimeException(message)

/**
 * Comment's
 */
class CommentNotFoundException(message: String) : RuntimeException(message)
class WrongReasonException(message: String) : RuntimeException(message)
class CantCommentNoteException(message: String): RuntimeException(message)

/**
 * Note's
 */
class NoteNotFoundException(message: String) : RuntimeException(message)
class NoteAccessDeniedException(message: String) : RuntimeException(message)