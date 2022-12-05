data class Assignment(val left: Int, val right: Int) {
    companion object {
        fun parse(input: String) = Assignment(input.split('-')[0].toInt(), input.split('-')[1].toInt())
    }

    fun contains(other: Assignment) = this.left <= other.left && this.right >= other.right
    fun overlapsWith(other: Assignment): Boolean = !(this.right < other.left || this.left > other.right)
}
data class AssignmentPair(val first: Assignment, val second: Assignment) {

    companion object {
        fun parse(input: String) = AssignmentPair(Assignment.parse(input.split(',')[0]), Assignment.parse(input.split(',')[1]))
    }
    fun hasFullContainment() = first.contains(second) || second.contains(first)
    fun hasOverlap() = first.overlapsWith(second)
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.count { line -> AssignmentPair.parse(line).hasFullContainment() }
    }

    fun part2(input: List<String>): Int {
        return input.count { line -> AssignmentPair.parse(line).hasOverlap() }
    }

    val testInput = readInput("Day04_test")
    println("Test Part 1: " + part1(testInput))
    println("Test Part 2: " + part2(testInput))
    println()

    val input = readInput("Day04")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}
