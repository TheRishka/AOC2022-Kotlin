package day04


import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    /*
    //
    min(7, 8) <= min(24, 8) = true // actually true
    min(2, 37) <= min(75, 51) = true // actually true
    min(47,20) <= min(78, 39)



    // approach 1
    min(24, 8) - max(7, 8) = 8 - 8 = 0
    min(75, 51) - max(2, 37) = 51 - 37 = 14
    min(78, 39) - max(47, 20) = 39 - 47 = -8
    min(91, 53) - max(53, 34) = 53 - 53 = 0
    min(78, 39) - max(47, 20) = 39 - 47 = -8
    min(78, 39) - max(47, 20) = 39 - 47 = -8
        (min(endInclusive, other.endInclusive) - max(start, other.start))
        .coerceAtLeast(0.0)
     */

    fun isContainedIn(first: IntRange, second: IntRange) = when {
        first.first <= second.first && first.last >= second.last -> true
        second.first <= first.first && second.last >= first.last -> true
        else -> false
    }

    fun isOverlapped(first: IntRange, second: IntRange) = when {
        //2..75, 37..90
        //37..90, 2..75
        isContainedIn(first, second) -> true
        first.first <= second.first && first.last >= second.first -> true
        second.first <= first.first && second.last >= first.first -> true
        else -> false
    }

    fun part1(input: List<Pair<IntRange, IntRange>>): Int {
        var containedCounter = 0
        input.forEach {
            if (isContainedIn(it.first, it.second)) {
                containedCounter++
            }
        }
        return containedCounter
    }

    fun part2(input: List<Pair<IntRange, IntRange>>): Int {
        var overlapCounter = 0
        input.forEach {
            if (isOverlapped(it.first, it.second)) {
                overlapCounter++
            }
        }
        return overlapCounter
    }


    val input = readInput("day04/Day04")
    val elfPairs = input.chunked(1) {
        it[0].split(",")
    }.map {
        stringToIntRange(it[0]) to stringToIntRange(it[1])
    }
    println(part1(elfPairs))
    println(part2(elfPairs))
}

fun stringToIntRange(input: String) = with(input.split("-")) {
    IntRange(get(0).toInt(), get(1).toInt())
}
