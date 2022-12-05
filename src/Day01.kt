import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var maxCalories = 0
        var currentTotal = 0
        for (line in input) {
            if (line.isEmpty()) {
                maxCalories = max(maxCalories, currentTotal)
                currentTotal = 0
            } else {
                currentTotal += Integer.parseInt(line)
            }
        }

        return maxCalories
    }

    fun part2(input: List<String>): Int {
        val weights = mutableListOf<Int>()
        var current = 0
        for (line in input) {
            if (line.isEmpty()) {
                weights.add(current)
                current = 0
            } else {
                current += Integer.parseInt(line)
            }
        }
        return weights.sorted().takeLast(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}
