package calculator

interface Stack<E> {
    fun empty(): Boolean
    fun top(): E
    fun pop(): E
    fun push(item: E)
}
