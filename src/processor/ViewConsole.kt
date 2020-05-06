package processor

import java.math.BigDecimal

class ViewConsole {
    private val regexSplit = Regex("[ ,]+")

    fun run() {
        menuMain()
    }

    private fun menuMain() {
        mainMenu@ while (true) {
            print(
                """Choose operation
            |1. Addition
            |2. Subtraction
            |3. Multiplication
            |4. Scalar multiplication
            |5. Transposition
            |6. Determinant
            |7. Inversion
            |0. Exit
            |""".trimMargin()
            )
            try {
                val option = readLine()!!.trim()
                println()

                val result = when (option) {
                    "1", "2", "3" -> {
                        val result: Array<Array<BigDecimal>>
                        try {
                            print("Enter size of first matrix: ")
                            val aSize = readMatrixSizeFromConsole()
                            println("Enter first matrix:")
                            val a = readMatrixFromConsole(aSize[0], aSize[1])
                            print("Enter size of second matrix: ")
                            val bSize = readMatrixSizeFromConsole()
                            println("Enter second matrix:")
                            val b = readMatrixFromConsole(bSize[0], bSize[1])

                            result = when (option) {
                                "1" -> matrixAddition(a, b)
                                "2" -> matrixSubtraction(a, b)
                                "3" -> matrixMultiplication(a, b)
                                else -> throw IllegalArgumentException("Something went wrong")
                            }
                        } catch (e: IncompatibleMatricesException) {
                            println("\nError, incompatible matrices\n")
                            continue@mainMenu
                        }
                        result
                    }
                    "5" -> {
                        val optionTrans = menuTransposition()
                        if (optionTrans == "0") continue@mainMenu

                        print("Enter size of matrix: ")
                        val size = readMatrixSizeFromConsole()
                        println("Enter matrix:")
                        val matrix = readMatrixFromConsole(size[0], size[1])
                        val result = when (optionTrans) {
                            "1" -> matrixTransMainDiagonal(matrix)
                            "2" -> matrixTransSideDiagonal(matrix)
                            "3" -> matrixTransVerticalLine(matrix)
                            "4" -> matrixTransHorizontalLine(matrix)
                            else -> throw Exception("Something went wrong")
                        }
                        result
                    }
                    "4", "6", "7" -> {
                        print("Enter size of matrix: ")
                        val size = readMatrixSizeFromConsole()
                        println("Enter matrix:")
                        val matrix = readMatrixFromConsole(size[0], size[1])
                        when (option) {
                            "4" -> {
                                println("Enter scalar:")
                                matrixScalarMultiplication(matrix, readLine()!!.trim().toBigDecimal())
                            }
                            "6" -> arrayOf(arrayOf(matrixDeterminant(matrix)))
                            "7" -> {
                                val invertedMatrix = matrixInversion(matrix)
                                if (invertedMatrix.isEmpty()) throw NotInvertibleMatrixException()
                                else invertedMatrix
                            }
                            else -> throw Exception("Something went wrong")
                        }
                    }
                    "0" -> break@mainMenu
                    else -> {
                        println("Incorrect input, please try again\n")
                        continue@mainMenu
                    }
                }
                println("\nThe ${getOperationName(option)} result is:")
                printMatrix(result)
                println()
            } catch (e: NumberFormatException) {
                println("\nIncorrect input, please try again\n")
            } catch (e: IncorrectInputException) {
                println("\nIncorrect input, please try again\n")
            } catch (e: NotInvertibleMatrixException) {
                println("\nMatrix is not invertible\n")
            }
        }
    }

    private fun menuTransposition(): String {
        transMenu@ while (true) {
            print(
                """
            |1. Main diagonal
            |2. Side diagonal
            |3. Vertical line
            |4. Horizontal line
            |0. Return
            |Your choice: """.trimMargin()
            )
            val option = readLine()!!.trim()
            println()
            when (option) {
                "1", "2", "3", "4", "0" -> return option
                else -> {
                    println("Incorrect input, please try again\n")
                    continue@transMenu
                }
            }
        }
    }

    private fun getOperationName(option: String): String {
        return when (option) {
            "1" -> "addition"
            "2" -> "subtraction"
            "3" -> "multiplication"
            "4" -> "scalar multiplication"
            "5" -> "transposition"
            "6" -> "determinant"
            "7" -> "inversion"
            else -> throw IllegalArgumentException("Something went wrong")
        }
    }

    private fun readMatrixSizeFromConsole(): List<Int> {
        val mSize = readLine()!!.trim(' ', ',').split(regexSplit).map { value -> value.toInt() }
        return if (mSize.size != 2 || mSize[0] <= 0 || mSize[1] <= 0) throw IncorrectInputException() else mSize
    }

    private fun readMatrixFromConsole(rows: Int, cols: Int): Array<Array<BigDecimal>> {
        if (rows <= 0 || cols <= 0) throw IllegalArgumentException()
        val matrix = Array(rows) {
            readLine()!!.trim(' ', ',').split(regexSplit).map { value -> value.toBigDecimal() }.toTypedArray()
        }
        if (cols != matrix[0].size || !isMatrixCorrect(matrix)) throw IncorrectInputException()
        return matrix
    }

    private fun printMatrix(matrix: Array<Array<BigDecimal>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                print(matrix[i][j])
                if (j != matrix[i].lastIndex) print(' ')
                else println()
            }
        }
    }

    private class IncorrectInputException : Exception()
    private class NotInvertibleMatrixException : Exception()
}
