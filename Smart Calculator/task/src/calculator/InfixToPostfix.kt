package calculator

fun priority(token: Token): Int = when (token) {
    Token.Plus, Token.Minus -> 1
    Token.Multiply, Token.Divide -> 2
    else -> 0
}

fun infixToPostfix(input: List<Token>): List<Token> {
    val output = mutableListOf<Token>()
    val stack: Stack<Token> = StackImpl()
    stack.push(Token.None)
    for (t in input) {
        when (t) {
            is Token.Variable, is Token.Number -> output.add(t)
            is Token.BracketOpen -> stack.push(t)
            is Token.BracketClose -> {
                while (stack.top() != Token.None && stack.top() != Token.BracketOpen) {
                    output.add(stack.pop())
                }
                stack.pop()
            }
            else -> {
                if (priority(t) > priority(stack.top())) {
                    stack.push(t)
                } else {
                    while (stack.top() != Token.None && priority(t) <= priority(stack.top())) {
                        output.add(stack.pop())
                    }
                    stack.push(t)
                }
            }
        }
    }

    while (stack.top() != Token.None) {
        output.add(stack.pop())
    }

    return output
}
