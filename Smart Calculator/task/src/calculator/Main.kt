package calculator

fun main() {
    val symbolTable = mutableMapOf<String, Int>()

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

fun evaluateExpression(input: String, symbolTable: Map<String, Int>): Int {
    val tokens = compact(parse(normalize(input)))

    lex(tokens)

    val postfix = infixToPostfix(tokens)

    return evaluateExpression(postfix, symbolTable)
}

fun processAssignment(input: String, symbolTable: MutableMap<String, Int>) {
    Assignment.regex.matchEntire(input)?.let {
        val left = it.groups["left"]!!.value
        val right = it.groups["right"]!!.value
        if (right.isVariable()) {
            symbolTable[right]?.let { value ->
                symbolTable[left] = value
            } ?: println("Unknown variable")
        } else {
            symbolTable[left] = right.toInt()
        }
    } ?: println("Invalid assignment")
}

fun printHelp() {
    println("Here is an example of an expression that contains all possible operations:")
    println("3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)")
    println("The result is 121.")
}
