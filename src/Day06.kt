fun String.detect(windowSize: Int = 4): Int {
    return this.windowed(size = windowSize).indexOfFirst { it.length == it.toSet().size } + windowSize
}

fun main() {
    fun part1(input: List<String>): Int {
        return input[0].detect()
    }

    fun part2(input: List<String>): Int {
        return input[0].detect(windowSize = 14)
    }

    val testInput = readInput("Day06_test")
    println("Test Part 1: " + part1(testInput))
    println("Test Part 2: " + part2(testInput))

    println()

    val input = readInput("Day06")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}
