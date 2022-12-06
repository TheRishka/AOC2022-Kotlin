package day05


import readInput
import java.util.Stack
import kotlin.math.max
import kotlin.math.min

fun main() {
    val startStacksField = mutableMapOf(
        1 to Stack<Char>().apply {
            addAll(listOf('D', 'H', 'R', 'Z', 'S', 'P', 'W', 'Q').reversed())
        },
        2 to Stack<Char>().apply {
            addAll(listOf('F', 'H', 'Q', 'W', 'R', 'B', 'V').reversed())
        },
        3 to Stack<Char>().apply {
            addAll(listOf('H', 'S', 'V', 'C').reversed())
        },
        4 to Stack<Char>().apply {
            addAll(listOf('G', 'F', 'H').reversed())
        },
        5 to Stack<Char>().apply {
            addAll(listOf('Z', 'B', 'J', 'G', 'P').reversed())
        },
        6 to Stack<Char>().apply {
            addAll(listOf('L', 'F', 'W', 'H', 'J', 'T', 'Q').reversed())
        },
        7 to Stack<Char>().apply {
            addAll(listOf('N', 'J', 'V', 'L', 'D', 'W', 'T', 'Z').reversed())
        },
        8 to Stack<Char>().apply {
            addAll(listOf('F', 'H', 'G', 'J', 'C', 'Z', 'T', 'D').reversed())
        },
        9 to Stack<Char>().apply {
            addAll(listOf('H', 'B', 'M', 'V', 'P', 'W').reversed())
        },
    )

    fun part1(startField: Map<Int, Stack<Char>>, operations: List<Operation>): String {
        val operationField = startField.toMutableMap()
        operations.forEach { operation ->
            for (actionCount in 1..operation.amount) {
                val char = operationField[operation.from]!!.pop()
                operationField[operation.to]!!.push(char)
            }
        }
        var endString = ""
        operationField.forEach {
            endString += it.value.peek()
        }
        return endString
    }

    fun part2(startField: Map<Int, Stack<Char>>, operations: List<Operation>): String {
        val operationField = startField.toMutableMap()
        operations.forEach { operation ->
            val pickedItems = mutableListOf<Char>()
            for (actionCount in 1..operation.amount) {
                val char = operationField[operation.from]!!.pop()
                pickedItems.add(char)
            }
            operationField[operation.to]!!.addAll(pickedItems.reversed())
        }
        var endString = ""
        operationField.forEach {
            endString += it.value.peek()
        }
        return endString
    }

    val input = readInput("day05/Day05")
    val commands = input.drop(10)
    // move 2 from 2 to 1
    val operations = commands.map { comms ->
        val digits = comms.split(" ").filter {
            !(it.contains("move") || it.contains("from") || it.contains("to"))
        }.map {
            it.toInt()
        }
        Operation(
            amount = digits[0],
            from = digits[1],
            to = digits[2]
        )
    }
//    println(part1(startStacksField.toMap(), operations))
    println(part2(startStacksField.toMap(), operations))
}

data class Operation(
    val amount: Int,
    val from: Int,
    val to: Int
)
