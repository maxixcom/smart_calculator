package calculator

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
        if (!isValidExpression(input)) {
            println("Invalid expression")
            continue
        }
        val numbers = parseNumbers(input)
        println(numbers.sum())
    }

    println("Bye!")
}

fun isValidExpression(input: String): Boolean {
    val regex = "^([\\+\\-]?\\d+)(\\s+[\\+\\-]+\\s+\\d+)*$".toRegex()
    return regex.matches(input)
}

fun parseNumbers(input: String): List<Int> {
    var i = input.replace("\\+".toRegex(), "")
    i = i.replace("--", "")
    i = i.replace("([\\-\\+])\\s+(\\d+)".toRegex(), "$1$2")

    return i.split("\\s+".toRegex()).map(String::toInt)
}

fun printHelp() {
    println("The program calculates the sum of numbers")
}
