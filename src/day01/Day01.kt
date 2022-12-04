package day01

import readInput

fun main() {
    fun extractGnomeCalories(input: List<String>): Map<Int, Int> {
        var gnomeIndex = 0
        var gnomeCalories = mutableMapOf<Int, Int>()
        input.forEach {
            if (it.isEmpty()) {
                gnomeIndex++
            } else {
                gnomeCalories[gnomeIndex] = gnomeCalories.getOrDefault(gnomeIndex, 0) + it.toInt()
            }
        }
        return gnomeCalories
    }

    fun part1(input: List<String>): Int {
        val calories = extractGnomeCalories(input)
        var biggestAmount = 0
        calories.forEach {
            if (it.value > biggestAmount) {
                biggestAmount = it.value
            }
        }
        return biggestAmount
    }


    fun part2(input: List<String>) = extractGnomeCalories(input).values.toList().sortedDescending()

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input).subList(0, 3).sum())
}
