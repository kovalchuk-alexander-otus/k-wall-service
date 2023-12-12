package exception

class PostNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)
class WrongReasonException(message: String) : RuntimeException(message)
