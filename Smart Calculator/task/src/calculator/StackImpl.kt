package calculator

/**
 * Stack implementation based on ArrayDeque
 * https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-deque/
 */
class StackImpl<E> : Stack<E> {
    private val storage: ArrayDeque<E> = ArrayDeque()
    override fun empty(): Boolean = storage.isEmpty()
    override fun peek(): E = storage.last()
    override fun pop(): E = storage.removeLast()
    override fun push(item: E) = storage.addLast(item)
}
