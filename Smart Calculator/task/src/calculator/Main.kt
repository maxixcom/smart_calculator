package calculator

import java.math.BigInteger

fun main() {
    val symbolTable = mutableMapOf<String, BigInteger>()

    while (true) {
        val input = readLine()!!
        if (input.isBlank()) {
            continue
        }
        if (input.first() == '/') {
            when (input) {
                "/exit" -> break
                "/help" -> {
                    printHelp()
                    continue
                }
                else -> {
                    println("Unknown command")
                    continue
                }
            }
        }

        if (input.isAssignment()) {
            processAssignment(input, symbolTable)
            continue
        }
        try {
            println(evaluateExpression(input, symbolTable))
        } catch (e: Exception) {
            println(e.message)
        }
    }

    println("Bye!")
}

fun evaluateExpression(input: String, symbolTable: Map<String, BigInteger>): BigInteger {
    val tokens = compact(parse(normalize(input)))

    lex(tokens)

    val postfix = infixToPostfix(tokens)

    return evaluateExpression(postfix, symbolTable)
}

fun processAssignment(input: String, symbolTable: MutableMap<String, BigInteger>) {
    Assignment.regex.matchEntire(input)?.let {
        val left = it.groups["left"]!!.value
        val right = it.groups["right"]!!.value
        if (right.isVariable()) {
            symbolTable[right]?.let { value ->
                symbolTable[left] = value
            } ?: println("Unknown variable")
        } else {
            symbolTable[left] = right.toBigInteger()
        }
    } ?: println("Invalid assignment")
}

fun printHelp() {
    println("Here is an example of an expression that contains all possible operations:")
    println("3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)")
    println("The result is 121.")
}
