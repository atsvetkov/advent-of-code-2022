import java.util.Stack

typealias CrateStack = Stack<Char>

data class Crates(val crateStacks: List<CrateStack>) {
    fun apply(move: Move) {
        val fromStack = crateStacks[move.from-1]
        val toStack = crateStacks[move.to-1]
        move.take(fromStack).forEach(toStack::push)
    }

    override fun toString(): String {
        val result = StringBuilder()
        for ((index, crate) in crateStacks.withIndex()) {
            result.append("${index+1}: $crate")
            result.append("\r\n")
        }
        return result.toString()
    }

    fun topCrates() = crateStacks.map(CrateStack::peek).joinToString("")
}

sealed class Move(val amount: Int, val from: Int, val to: Int) {
    companion object {
        fun parseOneByOneMove(move: String): Move {
            val parts = move.split(' ')
            return OneByOneMove(amount = parts[1].toInt(), from = parts[3].toInt(), to = parts[5].toInt())
        }

        fun parseAtOnceMove(move: String): Move {
            val parts = move.split(' ')
            return AtOnceMove(amount = parts[1].toInt(), from = parts[3].toInt(), to = parts[5].toInt())
        }
    }

    abstract fun take(crateStack: CrateStack): List<Char>

    class OneByOneMove(amount: Int, from: Int, to: Int) : Move(amount, from, to) {
        override fun take(crateStack: CrateStack) = (1 .. amount).map { crateStack.pop() }
    }

    class AtOnceMove(amount: Int, from: Int, to: Int) : Move(amount, from, to) {
        override fun take(crateStack: CrateStack) = (1 .. amount).map { crateStack.pop() }.reversed()
    }
}

fun parseInitialState(stateLines: List<String>): Crates {
    val size = stateLines.last().split(' ').filter { !it.isNullOrBlank() }.size
    val crates = Array(size) { _ -> CrateStack()}
    for (line in stateLines.dropLast(1).reversed()) {
        for (crateIndex in 0 until size) {
            if (1+4*crateIndex >= line.length) continue
            val crate = line[1+4*crateIndex]
            if (!crate.isWhitespace()) crates[crateIndex].push(crate)
        }
    }

    return Crates(crates.toList())
}

fun parseMoves(moveLines: List<String>, init: (s: String) -> Move) = moveLines.map(init)

fun splitInput(input: List<String>): Pair<List<String>, List<String>> {
    var parsingState = true
    val stateLines = mutableListOf<String>()
    val moveLines = mutableListOf<String>()
    for (line in input) {
        if (line.isEmpty()) {
            parsingState = false
        } else if (parsingState) {
            stateLines.add(line)
        } else {
            moveLines.add(line)
        }
    }

    return Pair(stateLines, moveLines)
}

fun main() {
    fun part1(input: List<String>): String {
        val (stateLines, moveLines) = splitInput(input)
        val crates = parseInitialState(stateLines)
        val moves = parseMoves(moveLines) { Move.parseOneByOneMove(it) }
        moves.forEach { crates.apply(it) }

        return crates.topCrates()
    }

    fun part2(input: List<String>): String {
        val (stateLines, moveLines) = splitInput(input)
        val crates = parseInitialState(stateLines)
        val moves = parseMoves(moveLines) { Move.parseAtOnceMove(it) }
        moves.forEach { crates.apply(it) }

        return crates.topCrates()
    }

    val testInput = readInput("Day05_test")
    println("Test Part 1: " + part1(testInput))
    println("Test Part 2: " + part2(testInput))

    println()

    val input = readInput("Day05")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}
