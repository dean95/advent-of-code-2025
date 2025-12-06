fun main() {
    val numberRegex = "\\d+".toRegex()
    val whitespaceRegex = "\\s+".toRegex()

    fun getAlignment(input: List<String>): List<Alignment> {
        val rangesPerLine = input.map { line ->
            numberRegex.findAll(line).map { it.range }.toList()
        }

        if (rangesPerLine.isEmpty()) return emptyList()

        val alignments = mutableListOf<Alignment>()

        repeat(rangesPerLine.first().size) { colIndex ->
            val leftAligned = rangesPerLine.map { rangesInLine ->
                rangesInLine[colIndex].first
            }.distinct().size == 1

            alignments.add(if (leftAligned) Alignment.LEFT else Alignment.RIGHT)
        }

        return alignments
    }

    fun mapNumbers(numbers: List<String>, isRightAligned: Boolean): List<Long> {
        val maxWidth = numbers.maxOf { it.length }
        val paddedNumbers = numbers.map { number ->
            if (isRightAligned) {
                number.padStart(maxWidth, 'X')
            } else {
                number.padEnd(maxWidth, 'X')
            }
        }

        return (0 until maxWidth).map { columnIndex ->
            paddedNumbers.mapNotNull { paddedNumber ->
                paddedNumber[columnIndex].takeIf { it != 'X' }
            }.joinToString("").toLong()
        }
    }

    fun part1(input: List<String>): Long {
        val numberRows = input.dropLast(1).map { line ->
            line.split(whitespaceRegex)
                .filter { token -> token.isNotBlank() }
                .map { token -> token.toLong() }
        }

        val operators = input.last().split(whitespaceRegex)

        val columnResults = LongArray(numberRows.first().size)

        for (colIndex in operators.indices) {
            for (rowIndex in numberRows.indices) {
                when (operators[colIndex]) {
                    "*" -> if (columnResults[colIndex] == 0L) {
                        columnResults[colIndex] = numberRows[rowIndex][colIndex]
                    } else {
                        columnResults[colIndex] *= numberRows[rowIndex][colIndex]
                    }

                    "+" -> columnResults[colIndex] += numberRows[rowIndex][colIndex]
                }
            }
        }

        return columnResults.sum()
    }

    fun part2(input: List<String>): Long {
        val operatorsPerColumn = input.last().split(whitespaceRegex)
        val alignmentsPerColumn = getAlignment(input.dropLast(1))

        val numberRows = input.dropLast(1).map { line ->
            line.split(whitespaceRegex).filter { it.isNotBlank() }
        }

        return operatorsPerColumn.indices.sumOf { colIndex ->
            val numbersInColumn = numberRows.map { row -> row[colIndex] }
            val columnValues = mapNumbers(numbersInColumn, alignmentsPerColumn[colIndex] == Alignment.RIGHT)
            columnValues.fold(0L) { acc, value ->
                when (operatorsPerColumn[colIndex]) {
                    "*" -> {
                        if (acc == 0L) value else acc * value
                    }

                    "+" -> {
                        acc + value
                    }

                    else -> error("Illegal state")
                }
            }
        }
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

enum class Alignment {
    LEFT, RIGHT
}
