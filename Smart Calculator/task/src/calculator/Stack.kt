package calculator

interface Stack<E> {
    fun empty(): Boolean
    fun peek(): E
    fun pop(): E
    fun push(item: E)
}
