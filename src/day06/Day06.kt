package day06

import readInput

fun main() {

    fun findMarker(input: String, sizeOfWindow: Int): Int {
        input.windowedSequence(size = sizeOfWindow).forEachIndexed { index, window ->
            val distinct = window.toCharArray().distinct()
            if (window.toCharArray().distinct().size == sizeOfWindow) {
                return index + distinct.size
            }
        }
        return -1
    }

    fun part1(input: String) = findMarker(input, sizeOfWindow = 4)

    fun part2(input: String) = findMarker(input, sizeOfWindow = 14)

    val input = readInput("day06/Day06_test")
    println(part1(input[0]))
    println(part2(input[0]))
}