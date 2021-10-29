package calculator

sealed interface Token {
    class Number(val value: Int) : Token {
        override fun toString() = value.toString()
    }

    class Variable(val value: String) : Token {
        override fun toString() = value
    }

    object Multiply : Token {
        override fun toString() = "*"
    }

    object Divide : Token {
        override fun toString() = "*"
    }

    object Plus : Token {
        override fun toString() = "+"
    }

    object Minus : Token {
        override fun toString() = "-"
    }

    object BracketOpen : Token {
        override fun toString() = "("
    }

    object BracketClose : Token {
        override fun toString() = ")"
    }
}
