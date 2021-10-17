package calculator

val variables = mutableMapOf<String, Int>()

fun main() {

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
            println("Assignment")
            input.processAssignment()
            continue
        }

        if (!input.isValidExpression()) {
            println("Invalid expression")
            continue
        }

        val numbers = input.parseNumbers()
        println(numbers.sum())
    }

    println("Bye!")
}

fun String.processAssignment() {
    val regex = "^(?<left>[a-zA-Z]+)\\s*=\\s*(?<right>[a-zA-Z]+|\\d+)$".toRegex()
    regex.matchEntire(this)?.let {
        val left = it.groups["left"]!!.value
        val right = it.groups["right"]!!.value
        if (right.isVariable()) {
            variables[right]?.let { value ->
                variables[left] = value
            } ?: println("Unknown variable")
        } else {
            variables[left] = right.toInt()
        }
    } ?: println("Invalid assignment")
}

fun String.isVariable() = "[a-zA-Z]+".toRegex().matches(this)

fun String.isAssignment(): Boolean {
    val regex = "^[a-zA-Z]+\\s*=\\s*([a-zA-Z]+|\\d+)$".toRegex()
    return regex.matches(this)
}

fun String.isValidExpression(): Boolean {
//    val regex = "^([\\+\\-]?\\d+)(\\s+[\\+\\-]+\\s+\\d+)*$".toRegex()
    val regex = "^([\\+\\-]?([a-zA-Z]+|\\d+))(\\s+[\\+\\-]+\\s+([a-zA-Z]+|\\d+))*$".toRegex()
    return regex.matches(this)
}

fun String.parseNumbers(): List<Int> {
    var i = replace("\\+".toRegex(), "")
    i = i.replace("--", "")
    i = i.replace("([\\-\\+])\\s+([a-zA-Z]+|\\d+)".toRegex(), "$1$2")

    for ((k, v) in variables) {
        i = i.replace(k, v.toString())
    }

    i = i.replace("--", "")

    return i.split("\\s+".toRegex()).map(String::toInt)
}

fun printHelp() {
    println("The program calculates the sum of numbers")
}
