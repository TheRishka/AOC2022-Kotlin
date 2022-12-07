package day07

import readInput
import java.util.*

fun main() {

    val input = readInput("day07/Day07")
    val rootFolder = Folder(
        name = "/",
        files = mutableMapOf(),
        folders = mutableMapOf(),
        totalSize = 0L
    )
    var currentFolder: Stack<Folder> = Stack<Folder>().apply { add(rootFolder) }
    input.forEach { terminalInput ->
        val command = if (terminalInput.startsWith("$")) terminalInput else null
        val commandOutput = if (command.isNullOrEmpty()) terminalInput else null
        command?.let {
            when (it) {
                "$ cd /" -> Unit
                "$ ls" -> {
                    // Listing data for CURRENT FOLDER
                    // Do nothing as currentFolder should be set
                }

                "$ cd .." -> {
                    currentFolder.pop()
                }

                else -> {
                    // CD somewhere?
                    val folderName = command?.drop(5)
                    if (currentFolder.peek().folders.containsKey(folderName).not()) {
                        println("ERRORR NO SUCH FOLDER!!!!!")
                    } else {
                        currentFolder.push(
                            currentFolder.peek().folders[folderName]
                        )
                    }
                }
            }
        }
        commandOutput?.let { output ->
            when {
                output.startsWith("dir") -> {
                    val dirName = output.drop(4)
                    currentFolder.peek().folders =
                        currentFolder.peek().folders.toMutableMap().apply {
                            put(
                                dirName, Folder(
                                    name = dirName,
                                    files = mutableMapOf(),
                                    folders = mutableMapOf(),
                                    totalSize = 0,
                                )
                            )
                        }
                }

                output.first().isDigit() -> {
                    // It's a file with filesize
                    val fileSize = output.filter {
                        it.isDigit()
                    }.toLong()
                    val fileName = output.filter {
                        it.isDigit().not()
                    }.trim()
                    currentFolder.peek().addFile(fileSize, fileName)
                }
            }
        }
    }
    println(part1(currentFolder[0]))
    println(part2(currentFolder[0]))
}

fun calculateFolderTotalSize(
    folder: Folder,
    calculationsCallback: (Folder, Long) -> Unit
): Long {
    var size = folder.totalSize
    folder.folders.forEach {
        size += calculateFolderTotalSize(it.value, calculationsCallback)
    }
    calculationsCallback(folder, size)
    return size
}

fun part1(inputRootFolder: Folder): Long {
    val listOfDirsWithSizeThreshold = mutableListOf<Pair<String, Long>>()
    calculateFolderTotalSize(inputRootFolder) { folder, size ->
        if (size <= 100000) {
            listOfDirsWithSizeThreshold.add(
                folder.name to size
            )
        }
    }
    return listOfDirsWithSizeThreshold.sumOf {
        it.second
    }
}

fun part2(inputRootFolder: Folder): Long {
    val diskAvailable = 70000000
    val requiredAtLeast = 30000000
    val listOfDirsWithSizes = mutableListOf<Pair<String, Long>>()
    inputRootFolder.totalSize = calculateFolderTotalSize(inputRootFolder) { folder, size ->
        listOfDirsWithSizes.add(
            folder.name to size
        )
    }
    val totalOccupiedSpace = inputRootFolder.totalSize
    val diskRequiredToBeFreed = totalOccupiedSpace - requiredAtLeast
    val dirThatFit = listOfDirsWithSizes.filter {
        diskAvailable - (totalOccupiedSpace - it.second) >= requiredAtLeast
    }.minBy {
        it.second
    }
    return dirThatFit.second
}
//1117448

data class FileInfo(
    var name: String,
    var size: Long,
)

data class Folder(
    var name: String,
    var files: MutableMap<String, FileInfo>,
    var folders: MutableMap<String, Folder>,
    var totalSize: Long
) {
    fun addFile(size: Long, fileName: String) {
        files[fileName] = FileInfo(
            name = fileName,
            size = size
        )
        totalSize += size
    }
}