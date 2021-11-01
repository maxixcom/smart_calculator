package calculator

import java.math.BigInteger

fun evaluateExpression(input: List<Token>, symbolTable: Map<String, BigInteger>): BigInteger {
    val stack: Stack<BigInteger> = StackImpl()
    stack.push(BigInteger.ZERO)
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
            is Token.Power -> {
                val right = stack.pop()
                val left = stack.pop()
                stack.push(left.pow(right.toInt()))
            }
            else -> throw Exception("Unexpected token: $t")
        }
    }
    return stack.top()
}
