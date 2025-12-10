import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

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

    fun part2(input: List<String>): Long {
        val points = parseInput(input)

        val borderPoints = mutableSetOf<Point>()

        for (i in points.indices) {
            val p1 = points[i]
            val p2 = if (i == points.lastIndex) points[0] else points[i + 1]

            if (p1.r == p2.r) {
                val row = p1.r
                val start = min(p1.c, p2.c)
                val end = max(p1.c, p2.c)
                for (c in start..end) {
                    borderPoints.add(Point(row, c))
                }
            } else if (p1.c == p2.c) {
                val col = p1.c
                val start = min(p1.r, p2.r)
                val end = max(p1.r, p2.r)
                for (r in start..end) {
                    borderPoints.add(Point(r, col))
                }
            }
        }


        val rows = borderPoints.maxOf { it.r + 1 }
        val columns = borderPoints.maxOf { it.c + 1 }

        val outsideChars = mutableSetOf<Point>()

        for (r in 0 until rows) {
            for (c in 0 until columns) {
                val element = Point(r, c)
                if (element in borderPoints || element in outsideChars) break
                outsideChars.add(element)
            }
        }

        for (r in (rows - 1) downTo 0) {
            for (c in 0 until columns) {
                val element = Point(r, c)
                if (element in borderPoints || element in outsideChars) break
                outsideChars.add(element)
            }
        }

        for (c in 0 until columns) {
            for (r in 0 until rows) {
                val element = Point(r, c)
                if (element in borderPoints || element in outsideChars) break
                outsideChars.add(element)
            }
        }

        for (c in (columns - 1) downTo 0) {
            for (r in 0 until rows) {
                val element = Point(r, c)
                if (element in borderPoints || element in outsideChars) break
                outsideChars.add(element)
            }
        }

        var maxArea = 0L
        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]

                val corner1 = Point(p1.r, p2.c)
                val corner2 = Point(p2.r, p1.c)

                if (corner1 !in outsideChars && corner2 !in outsideChars) {
                    val width = (p1.c - p2.c + 1).absoluteValue
                    val height = (p1.r - p2.r + 1).absoluteValue

                    val area = width.toLong() * height
                    if (area > maxArea) {
                        maxArea = area
                    }
                }
            }
        }

        return maxArea
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
