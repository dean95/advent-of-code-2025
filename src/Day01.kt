fun main() {
    fun part1(input: List<String>): Int {
        var res = 0
        var sum = 50

        input.forEach { line ->
            val direction = line.first()
            val num = line.substring(1).toInt()

            when (direction) {
                'L' -> {
                    sum = ((sum - num) % 100 + 100) % 100
                }

                'R' -> {
                    sum = (sum + num) % 100
                }
            }

            if (sum == 0) res++
        }

        return res
    }

    fun part2(input: List<String>): Int {
        var res = 0
        var sum = 50

        input.forEach { line ->
            val direction = line.first()
            val num = line.substring(1).toInt()

            when (direction) {
                'L' -> {
                    res += Math.floorDiv(sum - 1, 100) - Math.floorDiv(sum - num - 1, 100)
                    sum = Math.floorMod(sum - num, 100)
                }

                'R' -> {
                    res += (sum + num) / 100
                    sum = (sum + num) % 100
                }
            }
        }

        return res
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
