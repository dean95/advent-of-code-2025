fun main() {
    fun part1(input: List<String>): Int {

        fun explore(r: Int, c: Int, visited: MutableSet<Pair<Int, Int>> = mutableSetOf()): Int {
            if (r !in input.indices || c !in input[r].indices) return 0
            if (r to c in visited) return 0

            visited.add(r to c)

            val nR = r + 1
            if (nR >= input.size) return 0

            return when(input[nR][c]) {
                '.', 'S' -> explore(nR, c,visited)
                '^' -> {
                    var splits = 1
                    splits += explore(nR, c - 1,visited)
                    splits += explore(nR, c + 1,visited)
                    splits
                }
                else -> error("Illegal state")
            }
        }

        for (r in input.indices) {
            for (c in input[r].indices) {
                if (input[r][c] == 'S') {
                    return explore(r, c)
                }
            }
        }

        error("Illegal state")
    }

    fun part2(input: List<String>): Long {
        fun explore(r: Int, c: Int, memo: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()): Long {
            if (r !in input.indices || c !in input[r].indices) return 0
            if (r to c in memo) return memo.getValue(r to c)

            val nR = r + 1
            if (nR >= input.size) return 1

            return when(input[r][c]) {
                '.', 'S' -> {
                    explore(nR, c, memo)
                }
                '^' -> {
                    val left = if (c - 1 >= 0) explore(nR, c - 1, memo) else 0
                    val right = if (c + 1 < input[r].length) explore(nR, c + 1, memo) else 0
                    val paths = left + right
                    memo[r to c] = paths
                    paths
                }
                else -> error("Illegal state")
            }

        }

        for (r in input.indices) {
            for (c in input[r].indices) {
                if (input[r][c] == 'S') {
                    return explore(r, c)
                }
            }
        }

        error("Illegal state")
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
