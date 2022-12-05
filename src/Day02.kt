fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { Shape.parse(it.split(' ')[0]).play(Shape.parse(it.split(' ')[1])) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { Shape.parse(it.split(' ')[0]).play(Shape.parse(it.split(' ')[0]).counterpartByOutcome(Outcome.parse(it.split(' ')[1]))) }
    }

//    val testInput = readInput("Day02_test")
//    println("Testing part 1: " + part1(testInput))

    val input = readInput("Day02")
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun Shape.play(myShape: Shape): Int {
    val result = myShape.score + when {
        this < myShape -> 6
        this > myShape -> 0
        else -> 3
    }

    return result
}

fun Shape.counterpartByOutcome(outcome: Outcome): Shape = when(outcome) {
    is Outcome.Win -> this.beatenBy()
    is Outcome.Loss -> this.beats()
    is Outcome.Draw -> this
}

sealed class Outcome(val value: Int, val score: Int) {
    companion object {
        fun parse(outcome: String) = when(outcome) {
            "X" -> Loss()
            "Y" -> Draw()
            "Z" -> Win()
            else -> throw Exception("Unknown outcome: $outcome")
        }
    }

    class Win : Outcome(1, 6)
    class Loss : Outcome(-1, 0)
    class Draw : Outcome(0, 3)
}

sealed class Shape(val score: Int) : Comparable<Shape> {
    companion object {
        fun parse(shape: String) = when(shape) {
            "A", "X" -> Rock()
            "B", "Y" -> Paper()
            "C", "Z" -> Scissors()
            else -> throw Exception("Unknown shape: $shape")
        }
    }

    abstract fun beatenBy(): Shape
    abstract fun beats(): Shape

    class Rock : Shape(1) {
        override fun beatenBy() = Paper()
        override fun beats() = Scissors()

        override fun compareTo(other: Shape): Int {
            return when (other) {
                is Rock -> 0
                is Paper -> -1
                is Scissors -> 1
            }
        }
    }

    class Paper : Shape(2) {
        override fun beatenBy() = Scissors()
        override fun beats() = Rock()

        override fun compareTo(other: Shape): Int {
            return when (other) {
                is Rock -> 1
                is Paper -> 0
                is Scissors -> -1
            }
        }
    }

    class Scissors : Shape(3) {
        override fun beatenBy() = Rock()
        override fun beats() = Paper()

        override fun compareTo(other: Shape): Int {
            return when (other) {
                is Rock -> -1
                is Paper -> 1
                is Scissors -> 0
            }
        }
    }
}