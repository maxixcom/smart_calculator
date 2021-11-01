package calculator

class Assignment {
    companion object {
        val regex = "^\\s*(?<left>[a-zA-Z]+)\\s*=\\s*(?<right>-?([a-zA-Z]+|\\d+))\\s*$".toRegex()
    }
}
