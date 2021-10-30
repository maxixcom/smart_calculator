package calculator

fun evaluateExpression(input: List<Token>, symbolTable: Map<String, Int>): Int {
    val stack: Stack<Int> = StackImpl()
    stack.push(0)
    for (t in input) {
        when (t) {
            is Token.Variable -> {
                symbolTable[t.value]?.let { stack.push(it) } ?: throw Exception("Unknown variable")
            }
            is Token.Number -> {
                stack.push(t.value)
            }
            is Token.Minus -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(left - right)
            }
            is Token.Plus -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(left + right)
            }
            is Token.Multiply -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(left * right)
            }
            is Token.Divide -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(left / right)
            }
            else -> throw Exception("Unexpected token: $t")
        }
    }
    return stack.top()
}
