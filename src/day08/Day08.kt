package day08

import readInput

fun main() {

    val input = readInput("day08/Day08")
    val matrixInput = arrayListOf<Int>()
    input.forEach {
        it.forEach { treeAsChar ->
            matrixInput.add(treeAsChar.digitToInt())
        }
    }

    val matrixCols = input[0].length
    val matrixRows = input.size

    /*
       0 1 2 3 4               0  1  2  3  4

    0  3 0 3 7 3            0  0  1  2  3  4
    1  2 5 5 1 2            1  5  6  7  8  9
    2  6 5 3 3 2            2  10 11 12 13 14
    3  3 3 5 4 9            3  15 16 17 18 19
    4  3 5 3 9 0            4  20 21 22 23 24
     */

    fun part1(matrixInput: ArrayList<Int>): Int {
        var visibleCounter = 0
        matrixInput.forEachIndexed { index, tree ->
            val treeData = calculateTreeData(
                inputMatrix = matrixInput,
                matrixCols = matrixCols,
                matrixRows = matrixRows,
                treeIndex = index
            )
            if (treeData.isVisible) {
                visibleCounter++
            }
        }
        return visibleCounter
    }

    fun part2(matrixInput: ArrayList<Int>): Int {
        val scenicScoreCounter = mutableListOf<TreeData>()
        matrixInput.forEachIndexed { index, tree ->
            val treeData = calculateTreeData(
                inputMatrix = matrixInput,
                matrixCols = matrixCols,
                matrixRows = matrixRows,
                treeIndex = index
            )
            if (treeData.isVisible) {
                scenicScoreCounter.add(treeData)
            }
        }

        scenicScoreCounter.sortByDescending {
            it.scenicScore
        }
        return scenicScoreCounter.maxBy {
            it.scenicScore
        }.scenicScore
    }

    println(part1(matrixInput = matrixInput))
    println(part2(matrixInput = matrixInput))
}

fun calculateTreeData(
    inputMatrix: ArrayList<Int>,
    matrixCols: Int,
    matrixRows: Int,
    treeIndex: Int
): TreeData {
    when {
        treeIndex / matrixRows == 0 || treeIndex / matrixRows == matrixRows - 1 -> {
            // It's on top or bottom
            return TreeData(isVisible = true, scenicScore = 0)
        }

        treeIndex % matrixCols == 0 || treeIndex % matrixCols == matrixCols - 1 -> {
            // It's on left or right
            return TreeData(isVisible = true, scenicScore = 0)
        }

        else -> {
            // Meaning it's somewhere in the middle.
            var isVisibleFromLeft = true
            var isVisibleFromTop = true
            var isVisibleFromRight = true
            var isVisibleFromBottom = true
            var treesToTheLeft = 0
            var treesToTheRight = 0
            var treesToTheTop = 0
            var treesToTheBottom = 0
            val heightOfOurTree = inputMatrix[treeIndex]

            var indexOfTreeToCheck = treeIndex - 1
            // Check from Left:
            do {
                val heightOfTreeToCheck = inputMatrix[indexOfTreeToCheck]
                treesToTheLeft++
                if (heightOfTreeToCheck >= heightOfOurTree) {
                    isVisibleFromLeft = false
                    break
                }
            } while (indexOfTreeToCheck-- % matrixCols > 0)

            indexOfTreeToCheck = treeIndex + 1
            // Check from Right:
            do {
                val heightOfTreeToCheck = inputMatrix[indexOfTreeToCheck]
                treesToTheRight++
                if (heightOfTreeToCheck >= heightOfOurTree) {
                    isVisibleFromRight = false
                    break
                }
                indexOfTreeToCheck
            } while (indexOfTreeToCheck++ % matrixCols < matrixCols - 1)

            indexOfTreeToCheck = treeIndex - matrixCols
            // Check from Top
            do {
                val heightOfTreeToCheck = inputMatrix[indexOfTreeToCheck]
                treesToTheTop++
                if (heightOfTreeToCheck >= heightOfOurTree) {
                    isVisibleFromTop = false
                    break
                }
                indexOfTreeToCheck -= matrixCols
            } while (indexOfTreeToCheck / matrixRows >= 0 && indexOfTreeToCheck > 0)

            // Check from Bottom
            indexOfTreeToCheck = treeIndex + matrixCols
            do {
                val heightOfTreeToCheck = inputMatrix[indexOfTreeToCheck]
                treesToTheBottom++
                if (heightOfTreeToCheck >= heightOfOurTree) {
                    isVisibleFromBottom = false
                    break
                }
                indexOfTreeToCheck += matrixCols
            } while (indexOfTreeToCheck / matrixRows < matrixRows && indexOfTreeToCheck > 0)

            return TreeData(
                isVisible = isVisibleFromBottom || isVisibleFromTop || isVisibleFromRight || isVisibleFromLeft,
                scenicScore = treesToTheBottom * treesToTheLeft * treesToTheRight * treesToTheTop
            )
        }
    }
}

data class TreeData(
    val isVisible: Boolean,
    val scenicScore: Int,
)