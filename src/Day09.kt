import kotlin.math.absoluteValue
import kotlin.math.max

fun main() {
    data class Point(val r: Int, val c: Int)

    fun parseInput(input: List<String>) = input.map {
        val (c, r) = it.split(',').map { it.toInt() }
        Point(r, c)
    }

    fun part1(input: List<String>): Long {
        val points = parseInput(input)

        var maxArea = 0L
        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]

                val width = (p1.c - p2.c + 1).absoluteValue
                val height = (p1.r - p2.r + 1).absoluteValue

                val area = width.toLong() * height
                maxArea = max(maxArea, area)
            }
        }

        return maxArea
    }

    val input = readInput("Day09")
    part1(input).println()
}
