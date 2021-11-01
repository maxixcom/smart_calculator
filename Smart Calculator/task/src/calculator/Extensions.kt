package calculator

fun String.isVariable() = "[a-zA-Z]+".toRegex().matches(this)

fun String.isNumber() = "\\d+".toRegex().matches(this)

fun String.isAssignment() = Assignment.regex.matchEntire(this)?.let { true } ?: false
