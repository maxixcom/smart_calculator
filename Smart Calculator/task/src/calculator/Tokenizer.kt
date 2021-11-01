package calculator

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
                value.isNumber() -> tokens.add(Token.Number(value.toBigInteger()))
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
                '^' -> Token.Power
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
        if (t == Token.Minus) {
            m++
            continue
        }
        if (t == Token.Plus) {
            p++
            continue
        }
        if (p > 0 || m > 0) {
            if (m % 2 == 1) {
                output.add(Token.Minus)
            } else {
                output.add(Token.Plus)
            }
            p = 0
            m = 0
        }
        output.add(t)
    }
    return output
}

fun lex(tokens: List<Token>) {
    when (tokens.first()) {
        is Token.Variable, is Token.Number,
        is Token.BracketOpen,
        is Token.Minus, is Token.Plus -> {
        }
        else -> throw Exception("Invalid expression")
    }

    var index = 0
    while (++index < tokens.size) {
        val left = tokens[index - 1]
        val right = tokens[index]
        when (left) {
            is Token.Variable -> {
                when (right) {
                    is Token.BracketOpen, is Token.BracketClose,
                    is Token.Minus, is Token.Plus,
                    is Token.Divide, is Token.Multiply,
                    is Token.Power -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.Number -> {
                when (right) {
                    is Token.BracketOpen, is Token.BracketClose,
                    is Token.Minus, is Token.Plus,
                    is Token.Divide, is Token.Multiply,
                    is Token.Power -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.BracketOpen -> {
                when (right) {
                    is Token.Variable, is Token.Number,
                    is Token.BracketOpen, is Token.BracketClose,
                    is Token.Minus, is Token.Plus -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.BracketClose -> {
                when (right) {
                    is Token.BracketClose,
                    is Token.Minus, is Token.Plus,
                    is Token.Divide, is Token.Multiply,
                    is Token.Power -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.Minus -> {
                when (right) {
                    is Token.Variable, is Token.Number,
                    is Token.BracketOpen -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.Plus -> {
                when (right) {
                    is Token.Variable, is Token.Number,
                    is Token.BracketOpen -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.Divide -> {
                when (right) {
                    is Token.Variable, is Token.Number,
                    is Token.BracketOpen -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.Multiply -> {
                when (right) {
                    is Token.Variable, is Token.Number,
                    is Token.BracketOpen -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
            is Token.Power -> {
                when (right) {
                    is Token.Variable, is Token.Number,
                    is Token.BracketOpen -> {
                        continue
                    }
                    else -> throw Exception("Invalid expression")
                }
            }
        }
    }
}
