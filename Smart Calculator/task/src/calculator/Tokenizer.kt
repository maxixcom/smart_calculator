package calculator

// fun String.isVariable() = "[a-zA-Z]+".toRegex().matches(this)
fun String.isNumber() = "\\d+".toRegex().matches(this)

// fun String.toTokens() {
//    var input = this
//    val tokens = mutableListOf<Token>()
//    val numberRegex = "\\s+".toRegex()
//    val variableRegex = "\\s+".toRegex()
//    var index = 0
//    while (index <= lastIndex) {
//        val ch = this[index]
//        if (ch.isWhitespace()) {
//            index++
//            continue
//        }
//        if (ch.isLetterOrDigit()) {
//            var value: String = "$ch"
//
//            while ((++index <= lastIndex) && this[index].isLetterOrDigit()) {
//                value += this[index]
//            }
//            when {
//                value.isVariable() -> tokens.add(Token.Variable(value))
//                value.is
//            }
//        }
//    }
// }
//
fun parse(input: String): List<Token> {
    val tokens = mutableListOf<Token>()
    var index = 0
    while (index <= input.lastIndex) {
        val ch = input[index]
        if (ch.isWhitespace()) {
            index++
            continue
        }
        if (ch.isLetterOrDigit()) {
            var value = "$ch"

            while ((++index <= input.lastIndex) && input[index].isLetterOrDigit()) {
                value += input[index]
            }
            when {
                value.isVariable() -> tokens.add(Token.Variable(value))
                value.isNumber() -> tokens.add(Token.Number(value.toInt()))
                else -> throw Exception("Invalid expression")
            }
            continue
        }
        tokens.add(
            when (ch) {
                '-' -> Token.Minus
                '+' -> Token.Plus
                '*' -> Token.Multiply
                '/' -> Token.Divide
                '(' -> Token.BracketOpen
                ')' -> Token.BracketClose
                else -> throw Exception("Invalid expression")
            }
        )
        index++
    }
    return tokens
}

fun normalize(input: String): String {
    var i = input
    i = i.replace("\\s+".toRegex(), "")
    if (input.count { it == '(' } != input.count { it == ')' }) {
        throw Exception("Invalid expression")
    }
    return i
}

fun compact(tokens: List<Token>): List<Token> {
    val output = mutableListOf<Token>()
    var m = 0
    var p = 0
    for (t in tokens) {
        if(t==Token.Minus) {
            m++
            continue
        }
        if(t==Token.Plus) {
            m++
            continue
        }
        if(p>0 || m>0) {
            if(m>p) {
                output.add(Token.Minus)
            }
            else {
                output.add(Token.Plus)
            }


        }
        if (t != Token.Minus || t != Token.Plus) {
            m=0
            p=0
            output.add(t)
            continue
        }
        else {

        }
    }
}

fun main() {
    val input = "1 +-+-+- (  2 * 3 )- 4"
    val i = normalize(input)
    println(input)
    println(parse(i))
}
