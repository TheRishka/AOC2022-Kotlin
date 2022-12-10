package day09


import readInput
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    val input = readInput("day09/Day09")
    val movements = input.flatMap {
        val movementAmount = it.drop(1).trim().toInt()
        val movement = when (it.first()) {
            'R' -> Movement.RIGHT

            'U' -> Movement.UP

            'L' -> Movement.LEFT

            'D' -> Movement.DOWN

            else -> error("Invalid input")
        }
        buildList {
            for (amount in 1..movementAmount) {
                add(movement)
            }
        }
    }


    fun debugPrint(snakeHead: Point, snakePoints: ArrayList<Point>) {
        for (y in -4..0) {
            for (x in 0..5) {
                val point = Point(x, y)
                if (point == snakeHead) {
                    print(" H ")
                } else {
                    val index = snakePoints.indexOfFirst { snakePoint ->
                        snakePoint == point
                    }
                    if (index != -1) {
                        print(" $index ")
                    }
                    if (snakePoints.none { it == point }) {
                        print(" . ")
                    }
                }
            }
            println()
        }
    }

    fun part1(movements: List<Movement>): Int {
        var tail = Point()
        var head = Point()

        val tailMovesHistory = mutableMapOf<Point, Int>()
        tailMovesHistory[tail] = 0
        movements.forEach { movement ->
            head = when (movement) {
                Movement.RIGHT -> head.copy(x = head.x + movement.changeX)
                Movement.LEFT -> head.copy(x = head.x + movement.changeX)
                Movement.UP -> head.copy(y = head.y + movement.changeY)
                Movement.DOWN -> head.copy(y = head.y + movement.changeY)
            }

            calculateNewPointToMove(head, tail)?.let {
                tail = it
                tailMovesHistory[tail] = tailMovesHistory.getOrDefault(it, 0) + 1
            }
        }
        return tailMovesHistory.size
    }

    fun part2(movements: List<Movement>): Int {
        val snakePoints = ArrayList<Point>()
        for (count in 0..8) {
            snakePoints.add(Point())
        }

        var tail = snakePoints.last()
        var snakeHead = Point()

        val tailMovesHistory = mutableMapOf<Point, Int>()
        tailMovesHistory[tail] = 0
        movements.forEach { movement ->
            snakeHead = when (movement) {
                Movement.RIGHT -> snakeHead.copy(x = snakeHead.x + movement.changeX)
                Movement.LEFT -> snakeHead.copy(x = snakeHead.x + movement.changeX)
                Movement.UP -> snakeHead.copy(y = snakeHead.y + movement.changeY)
                Movement.DOWN -> snakeHead.copy(y = snakeHead.y + movement.changeY)
            }
            snakePoints.forEachIndexed { index, snakePoint ->
                val snakePartHead = if (index == 0) {
                    snakeHead
                } else {
                    snakePoints[index - 1]
                }
                calculateNewPointToMove(snakePartHead, snakePoint)?.let {
                    snakePoints[index] = it
                    if (index == snakePoints.lastIndex) {
                        tail = it
                        tailMovesHistory[tail] = tailMovesHistory.getOrDefault(it, 0) + 1
                    }
                }
            }
            //debugPrint(snakeHead, snakePoints)
        }
        return tailMovesHistory.size
    }

    println(part1(movements))
    println(part2(movements))
}

fun calculateNewPointToMove(head: Point, tail: Point) = when {
    //vertical/horizontal distance > 1
    head.y == tail.y && head.x - 2 == tail.x -> {
        Point(head.x - 1, head.y)
    }

    head.y == tail.y && head.x + 2 == tail.x -> {
        Point(head.x + 1, head.y)
    }

    head.x == tail.x && head.y + 2 == tail.y -> {
        Point(head.x, head.y + 1)
    }

    head.x == tail.x && head.y - 2 == tail.y -> {
        Point(head.x, head.y - 1)
    }
    // diagonal distance > 2
    head.distanceTo(tail) > 1 -> {
        val suitablePointsToMove = listOf(
            Point(head.x - 1, head.y),
            Point(head.x + 1, head.y),
            Point(head.x, head.y - 1),
            Point(head.x, head.y + 1),
            Point(head.x - 1, head.y + 1),
            Point(head.x - 1, head.y - 1),
            Point(head.x + 1, head.y + 1),
            Point(head.x + 1, head.y - 1),
        ).sortedBy { suitablePoint ->
            suitablePoint.distanceTo(tail)
        }
        val newTailPoints = listOf(
            Point(tail.x + 1, tail.y + 1), // Up-Right
            Point(tail.x - 1, tail.y + 1), // Up-Left
            Point(tail.x + 1, tail.y - 1), // Down-Right
            Point(tail.x - 1, tail.y - 1), // Down-Left
        )
        val newTailPoint = newTailPoints.first { it == suitablePointsToMove[0] || it == suitablePointsToMove[1] }
        newTailPoint
    }

    else -> null
}

data class Point(
    val x: Int = 0,
    val y: Int = 0,
) {

    fun distanceTo(to: Point): Int {
        return sqrt(((x - to.x).toFloat().pow(2)) + ((y - to.y).toFloat().pow(2))).roundToInt()
    }
}

enum class Movement(val changeX: Int = 0, val changeY: Int = 0) {
    RIGHT(changeX = 1), LEFT(changeX = -1), UP(changeY = -1), DOWN(changeY = 1),
}