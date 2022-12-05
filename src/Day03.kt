fun main() {
    fun Char.priority() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> 0
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val size = line.length
            val wrongItems = line.substring(0, size/2).toCharArray().intersect(line.substring(size/2).toList().toSet())
            check(wrongItems.size == 1) { "Only one wrong item is expected" }
            wrongItems.first().priority()
        }
    }

    fun part2(input: List<String>): Int {
        return input
            .chunked(3)
            .sumOf { sacks ->
                val commonItems = sacks[0].toSet().intersect(sacks[1].toSet()).intersect(sacks[2].toSet())
                check(commonItems.size == 1) { "Only one common item is expected" }
                commonItems.first().priority()
            }
    }

    val input = readInput("Day03")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}


