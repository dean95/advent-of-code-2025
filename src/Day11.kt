fun main() {
    fun parseInput(input: List<String>): Map<String, List<String>> {
        val graph = mutableMapOf<String, List<String>>()

        for (line in input) {
            val node = line.substringBefore(':')
            val neighbours = line.substringAfter(": ").split(' ')
            graph[node] = neighbours
        }

        return graph
    }

    fun part1(input: List<String>): Int {
        val graph = parseInput(input)

        fun countPathsFrom(src: String): Int {
            if (src == "out") return 1

            var totalPaths = 0
            for (neighbour in graph.getValue(src)) {
                totalPaths += countPathsFrom(neighbour)
            }

            return totalPaths
        }

        return countPathsFrom("you")
    }

    fun part2(input: List<String>): Long {
        val graph = parseInput(input)

        data class State(val node: String, val hasSeenFft: Boolean, val hasSeenDac: Boolean)

        fun countValidPaths(
            node: String,
            hasSeenFft: Boolean,
            hasSeenDac: Boolean,
            memo: MutableMap<State, Long> = mutableMapOf()
        ): Long {
            val updatedHasSeenFft = hasSeenFft || node == "fft"
            val updatedHasSeenDac = hasSeenDac || node == "dac"

            val state = State(node, updatedHasSeenFft, updatedHasSeenDac)
            if (state in memo) return memo.getValue(state)

            if (node == "out") {
                val result = if (updatedHasSeenFft && updatedHasSeenDac) 1L else 0
                memo[state] = result
                return result
            }

            var totalPaths = 0L
            for (neighbour in graph.getValue(node)) {
                totalPaths += countValidPaths(neighbour, updatedHasSeenFft, updatedHasSeenDac, memo)
            }

            memo[state] = totalPaths
            return totalPaths
        }

        return countValidPaths("svr", hasSeenFft = false, hasSeenDac = false)
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
