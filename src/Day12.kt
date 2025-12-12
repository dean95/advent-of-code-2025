fun main() {

    data class Area(val width: Int, val length: Int, val shapes: List<Int>)

    fun part1(input: List<String>): Int {
        val regionCellCounts = input
            .takeWhile { 'x' !in it }
            .chunked(5) { block ->
                block
                    .drop(1)
                    .dropLast(1)
                    .joinToString("")
                    .count { it == '#' }
            }

        val areas = input
            .takeLastWhile { it.isNotBlank() }
            .map { line ->
                val (sizePart, shapesPart) = line.split(": ")
                val (width, length) = sizePart.split('x').map { it.toInt() }
                val shapeCounts = shapesPart.split(' ').map { it.toInt() }
                Area(width, length, shapeCounts)
            }

        return areas.count { area ->
            val usableWidth = area.width / 3 * 3
            val usableLength = area.length / 3 * 3
            val usableBlocks = usableWidth * usableLength

            val requiredFullBlocks = area.shapes.mapIndexed { _, count -> count * 9 }.sum()
            val requiredCells = area.shapes.mapIndexed { index, count -> count * regionCellCounts[index] }.sum()

            when {
                usableBlocks >= requiredFullBlocks -> true
                requiredCells > area.width * area.length -> false
                else -> TODO("Try all placements")
            }
        }
    }

    val input = readInput("Day12")
    part1(input).println()
}
