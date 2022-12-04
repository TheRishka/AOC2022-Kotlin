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

    /**
     * X means you need to lose,
     * Y means you need to end the round in a draw,
     * Z means you need to win. Good luck!"
     */
    val doIneedToWinMap = mapOf(
        'X' to -1, // I need to loose
        'Y' to 0, // I need to draw
        'Z' to 1 // I need to win
    )
    val loseMap = mapOf(
        'A' to 'Z', // Rock <-- Scissors LOOSE
        'B' to 'X', // Paper <-- Rock LOOSE
        'C' to 'Y' // Scissors <-- Paper LOOSE
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
            total + calculateMatchScore(strategyMapValues.first(), strategyMapValues.last())
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { total, strategyMapValues ->
            val myInput = strategyMapValues.last()
            val enemyInput = strategyMapValues.first()
            val myInputAccordingToPlan = when (doIneedToWinMap[myInput]!!) {
                1 -> {
                    // win
                    winMap[enemyInput]
                }

                -1 -> {
                    // lose
                    loseMap[enemyInput]
                }

                0 -> {
                    // draw
                    drawMap[enemyInput]
                }

                else -> 'X'
            }
            total + calculateMatchScore(strategyMapValues.first(), myInputAccordingToPlan!!)
        }
    }

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}