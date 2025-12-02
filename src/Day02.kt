fun main() {
    fun parseInput(input: List<String>): List<Pair<Long, Long>> =
        input.first().split(',').map { ids ->
            val (firstId, lastId) = ids.split('-')
            firstId.toLong() to lastId.toLong()
        }

    fun isValidPart1(id: Long): Boolean {
        val idString = id.toString()

        if (idString.length % 2 != 0) return true

        var l = 0
        var r = idString.length / 2

        while (r < idString.length) {
            if (idString[l++] != idString[r++]) return true
        }

        return false
    }

    fun part1(input: List<String>): Long {
        val ids = parseInput(input)

        var res = 0L

        for ((firstId, lastId) in ids) {
            for (id in firstId..lastId) {
                if (!isValidPart1(id)) res += id
            }
        }

        return res
    }

    fun isValidPart2(id: Long): Boolean {
        val idString = id.toString()

        if (idString.length <= 1) return true

        val doubled = idString + idString
        return !doubled.substring(1, doubled.length - 1).contains(idString)
    }

    fun part2(input: List<String>): Long {
        val ids = parseInput(input)

        var res = 0L

        for ((firstId, lastId) in ids) {
            for (id in firstId..lastId) {
                if (!isValidPart2(id)) res += id
            }
        }

        return res
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
