fun main() {
    data class MachineDiagram(
        val lightDiagram: String,
        val schematics: List<List<Int>>,
        val joltages: List<Int>
    )

    fun parseInput(input: List<String>): List<MachineDiagram> {
        val bracketRegex = """\[(.*?)]""".toRegex()
        val parenRegex = """\((.*?)\)""".toRegex()
        val curlyRegex = """\{(.*?)}""".toRegex()

        return input.map { line ->
            val lightDiagram = bracketRegex.find(line)?.groupValues?.get(1) ?: error("")
            val schematics = parenRegex.findAll(line)
                .map { match ->
                    match.groupValues[1]
                        .split(',')
                        .filter { it.isNotBlank() }
                        .map { it.trim().toInt() }
                }.toList()
            val joltages = curlyRegex.find(line)
                ?.groupValues?.get(1)
                ?.split(',')
                ?.map { it.trim().toInt() } ?: error("")

            MachineDiagram(lightDiagram, schematics, joltages)
        }
    }

    fun part1(input: List<String>): Int {
        val machineDiagrams = parseInput(input)

        fun dfs(
            pattern: String,
            commands: List<List<Int>>,
            visiting: MutableSet<String> = mutableSetOf(),
            memo: MutableMap<String, Int> = mutableMapOf()
        ): Int {
            if (pattern.all { it == '.' }) return 0
            if (pattern in visiting) return -1
            if (pattern in memo) return memo.getValue(pattern)

            visiting.add(pattern)

            var minPresses = -1
            for (command in commands) {
                val newPattern = pattern.toCharArray()
                for (i in command) {
                    newPattern[i] = if (newPattern[i] == '.') '#' else '.'
                }
                val subPresses = dfs(newPattern.concatToString(), commands, visiting, memo)
                if (subPresses != -1) {
                    val numPresses = subPresses + 1
                    if (minPresses == -1 || numPresses < minPresses) {
                        minPresses = numPresses
                    }
                }
            }

            visiting.remove(pattern)
            memo[pattern] = minPresses

            return minPresses
        }

        return machineDiagrams.sumOf { (lightDiagram, schematics, _) ->
            dfs(lightDiagram, schematics)
        }
    }

    val input = readInput("Day10")
    part1(input).println()
}
