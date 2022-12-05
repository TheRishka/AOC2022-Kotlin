package day03

import readInput

fun main() {

    val scoringMap = buildMap {
        var scoreChar = 'a'
        var score = 1
        while (scoreChar <= 'z') {
            put(scoreChar, score)
            ++score
            ++scoreChar
        }
        scoreChar = 'A'
        while (scoreChar <= 'Z') {
            put(scoreChar, score)
            ++score
            ++scoreChar
        }
    }

    fun calculateScore(inputMap: Map<Char, Int>) = inputMap.map {
        it.value * scoringMap[it.key]!!
    }.fold(0) { total, scoring ->
        total + scoring
    }

    fun part1(input: List<String>): Int {
        //jNNBMTNzvT |MIDDLE| qhQLhQLMQL
        var duplicateScore = 0
        input.forEach { rucksackContents ->
            val firstHalfValuesCountMap = mutableMapOf<Char, Int>()
            val duplicatesCountMap = mutableMapOf<Char, Int>()
            val firstHalf = rucksackContents.take(rucksackContents.length / 2)
            val secondHalf = rucksackContents.takeLast(rucksackContents.length / 2)
            firstHalf.forEach {
                firstHalfValuesCountMap[it] = firstHalfValuesCountMap.getOrDefault(it, 0) + 1
            }
            secondHalf.forEach {
                if (firstHalfValuesCountMap.getOrDefault(it, 0) >= 1) {
                    duplicatesCountMap[it] = 1
                    firstHalfValuesCountMap[it] = firstHalfValuesCountMap[it]!! - 1
                }
            }
            duplicateScore += calculateScore(duplicatesCountMap)
        }
        return duplicateScore
    }

    fun fillCounterMap(input: String): Map<Char, Int> {
        val counterMap = mutableMapOf<Char, Int>()
        return counterMap.apply {
            input.forEach {
                put(it, getOrDefault(it, 0) + 1)
            }
        }
    }

    fun part2(input: List<String>): Int {
        /*
        jNNBMTNzvTqhQLhQLMQL
        VCwnVRCGHHJTdsLtrdhrGdsq
        wFJZTbRcnJCbpwpFccZCBfBvPzfpgfgzzWvjSzNP
         */
        val slicedInput = input.chunked(3)
        var totalBadgeScore = 0
        slicedInput.forEach { elvesGroupInput ->
            val firstElf = fillCounterMap(elvesGroupInput[0])
            val secondElf = fillCounterMap(elvesGroupInput[1])
            val thirdElf = fillCounterMap(elvesGroupInput[2])
            firstElf.forEach {
                when {
                    secondElf.containsKey(it.key) && thirdElf.containsKey(it.key) -> {
                        totalBadgeScore += scoringMap[it.key]!!
                    }
                }
            }
        }
        return totalBadgeScore
    }

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}