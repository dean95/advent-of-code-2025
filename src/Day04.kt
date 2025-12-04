fun main() {
    fun findAt(input: List<CharArray>): List<Pair<Int, Int>> {
        val dirs = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1, -1 to -1, -1 to 1, 1 to -1, 1 to 1)

        val foundPositions = mutableListOf<Pair<Int, Int>>()
        for (r in input.indices) {
            for (c in input[r].indices) {
                if (input[r][c] != '@') continue

                var count = 0
                for ((dR, dC) in dirs) {
                    val nR = r + dR
                    val nC = c + dC

                    if (nR in input.indices && nC in input[nR].indices && input[nR][nC] == '@') count++
                }

                if (count < 4) foundPositions.add(r to c)
            }
        }

        return foundPositions
    }


    fun part1(input: List<String>): Int = findAt(input.map { it.toCharArray() }).count()

    fun part2(input: List<String>): Int {
        val mutableInput = input.map { it.toCharArray() }

        var removedIndices = findAt(mutableInput)
        var totalCount = 0
        while (removedIndices.isNotEmpty()) {
            totalCount += removedIndices.count()
            for ((r, c) in removedIndices) {
                mutableInput[r][c] = '.'
            }
            removedIndices = findAt(mutableInput)
        }

        return totalCount
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
