fun main() {
    data class Point(val x: Long, val y: Long, val z: Long)

    data class Edge(val i: Int, val j: Int, val distSquared: Long)

    class UnionFind(n: Int) {
        private val parent = IntArray(n) { it }
        private val size = IntArray(n) { 1 }

        fun find(x: Int): Int {
            if (parent[x] != x) {
                parent[x] = find(parent[x])
            }
            return parent[x]
        }

        fun union(x: Int, y: Int): Boolean {
            val rootX = find(x)
            val rootY = find(y)

            if (rootX == rootY) return false

            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY
                size[rootY] += size[rootX]
            } else {
                parent[rootY] = rootX
                size[rootX] += rootY
            }

            return true
        }
    }

    fun sqr(x: Long) = x * x

    fun squaredDistance(a: Point, b: Point): Long {
        val dx = a.x - b.x
        val dy = a.y - b.y
        val dz = a.z - b.z

        return sqr(dx) + sqr(dy) + sqr(dz)
    }

    fun parsePoints(input: List<String>) = input.map { line ->
        val (x, y, z) = line.split(',').map { it.toLong() }
        Point(x, y, z)
    }

    fun buildSortedEdges(points: List<Point>): List<Edge> {
        val edges = mutableListOf<Edge>()
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val distSquared = squaredDistance(points[i], points[j])
                edges.add(Edge(i, j, distSquared))
            }
        }

        return edges.sortedBy { it.distSquared }
    }

    fun part1(input: List<String>): Long {
        val points = parsePoints(input)
        val edges = buildSortedEdges(points)

        val uf = UnionFind(points.size)
        val maxConnections = 1000
        var edgesUsed = 0

        for (e in edges) {
            uf.union(e.i, e.j)
            edgesUsed++
            if (edgesUsed == maxConnections) break
        }

        val groupSizes = mutableMapOf<Int, Int>()

        for (i in points.indices) {
            val root = uf.find(i)
            groupSizes[root] = (groupSizes[root] ?: 0) + 1
        }

        return groupSizes.values.sortedDescending().take(3).fold(1) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val points = parsePoints(input)
        val edges = buildSortedEdges(points)

        val uf = UnionFind(points.size)
        var components = points.size

        for (e in edges) {
            if (uf.union(e.i, e.j)) {
                components--
                if (components == 1) {
                    val x1 = points[e.i].x
                    val x2 = points[e.j].x
                    return x1 * x2
                }
            }
        }

        error("Illegal state")
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
