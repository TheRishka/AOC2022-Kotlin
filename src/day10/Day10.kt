package day10

import readInput

fun main() {

    val input = readInput("day10/Day10_test")
    val tickActions = input.map {
        val type = when (it.take(4)) {
            "noop" -> TickActionType.NO_OP
            "addx" -> TickActionType.ADDX
            else -> error("Unknown tickType")
        }
        val value = if (type != TickActionType.NO_OP) {
            it.drop(4).trim().toInt()
        } else 0
        TickAction(
            type = type,
            value = value,
        )
    }

    fun part1(inputActions: List<TickAction>) {
        var X = 1
        var cycleCounter = 0
        val signalTriggers = listOf(
            20,
            60,
            100,
            140,
            180,
            220
        )
        var signalStrength = 0

        fun increaseSignalStrengthIfNeeded() {
            if (cycleCounter in signalTriggers) {
                signalStrength += (cycleCounter * X)
            }
        }

        inputActions.forEach { tickAction ->
            cycleCounter++
            increaseSignalStrengthIfNeeded()
            if (tickAction.type != TickActionType.NO_OP) {
                cycleCounter++
                X += tickAction.value
                increaseSignalStrengthIfNeeded()
            }
        }
        println("CycleCounter = $cycleCounter, X = $X, signalStrength = $signalStrength")
    }

    part1(tickActions)
    part2(tickActions)
}

enum class TickActionType {
    NO_OP,
    ADDX,
}

data class TickAction(
    val type: TickActionType,
    val value: Int = 0
)

fun part2(tickActions: List<TickAction>) {
    var cycleCounter = 1
    var spritePositions = 1
    tickActions.forEach { tickAction ->
        printCRTOutput(cycleCounter - 1, spritePositions)
        cycleCounter++
        if (tickAction.type != TickActionType.NO_OP) {
            printCRTOutput(cycleCounter - 1, spritePositions)
            cycleCounter++
            spritePositions += tickAction.value
        }
    }
}

fun printCRTOutput(index: Int, spriteIndex: Int) {
    val adjustedIndex = index % 40
    if (adjustedIndex == 0) {
        println()
    }

    if (adjustedIndex in spriteIndex - 1..spriteIndex + 1) {
        print("#")
    } else {
        print(".")
    }
}