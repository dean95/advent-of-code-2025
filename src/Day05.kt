import kotlin.math.max

fun main() {
    fun parseRanges(input: List<String>) =
        input.takeWhile { it.isNotBlank() }
            .map { rangeString ->
                val (start, end) = rangeString.split('-')
                LongRange(start.toLong(), end.toLong())
            }

    fun parseIds(input: List<String>) = input.takeLastWhile { it.isNotBlank() }.map { it.toLong() }

    fun part1(input: List<String>): Int {
        val ingredientRanges = parseRanges(input)
        val ingredientIds = parseIds(input)

        return ingredientIds.count { id -> ingredientRanges.any { range -> id in range } }
    }

    fun part2(input: List<String>): Long {
        val ingredientRanges = parseRanges(input).sortedBy { it.first }.map { it.first to it.last }
        val mergedIntervals = mutableListOf<Pair<Long, Long>>()

        var (s, e) = ingredientRanges.first()

        for (i in 1..ingredientRanges.lastIndex) {
            val (cS, cE) = ingredientRanges[i]

            if (cS <= e) {
                e = max(e, cE)
            } else {
                mergedIntervals.add(s to e)
                s = cS
                e = cE
            }
        }

        mergedIntervals.add(s to e)

        return mergedIntervals.sumOf { (start, end) -> end - start + 1 }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
