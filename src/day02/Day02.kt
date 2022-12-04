package day02


import readInput

fun main() {
    val shapeScore = mapOf(
        'X' to 1, // Rock
        'Y' to 2, // Paper
        'Z' to 3 // Scissors
    )
    val winMap = mapOf(
        'A' to 'Y', // Rock <-- Paper WIN
        'B' to 'Z', // Paper <-- Scissors WIN
        'C' to 'X' // Scissors <-- Rock WIN
    )
    val drawMap = mapOf(
        'A' to 'X', // Rock <-- Rock DRAW
        'B' to 'Y', // Paper <-- Paper DRAW
        'C' to 'Z' // Scissors <-- Scissors DRAW
    )

    fun calculateMatchScore(inputOpponent: Char, inputSelf: Char): Int {
        // 0 lose, 6 win, 3 draw
        val winChar = winMap[inputOpponent]!!
        val drawChar = drawMap[inputOpponent]!!
        val shape = shapeScore[inputSelf]!!
        return when (inputSelf) {
            winChar -> {
                // win
                6 + shape
            }

            drawChar -> {
                // draw
                3 + shape
            }

            else -> shape // lose
        }
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { total, strategyMapValues ->
//            println("Folding! Total -> $total, strategyVales -> $strategyMapValues")
            total + calculateMatchScore(strategyMapValues.first(), strategyMapValues.last())
        }
    }

    val input = readInput("day02/Day02")
    println(part1(input))
}