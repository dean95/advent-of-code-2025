fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { bank ->
            val digits = bank.toList().mapIndexed { i, c -> c.digitToInt() to i }
            val (firstDigit, firstDigitIndex) = digits.dropLast(1).maxBy { it.first }
            val (secondDigit, _) = digits.drop(firstDigitIndex + 1).maxBy { it.first }

            "$firstDigit$secondDigit".toInt()
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { bank ->
            val digits = bank.toList().mapIndexed { i, c -> c.digitToInt() to i }

            val res = StringBuilder()
            var lastDigitIndex = -1

            repeat(12) { i ->
                val (maxDigit, maxDigitIndex) = digits
                    .dropLast(11 - i)
                    .drop(lastDigitIndex + 1)
                    .maxBy { it.first }

                res.append(maxDigit)
                lastDigitIndex = maxDigitIndex
            }

            res.toString().toLong()
        }
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
