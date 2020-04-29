package processor

import processor.MatrixOperation.*

class ViewConsole : ViewInterface {
    private lateinit var controller: Controller

    override fun initialize(controller: Controller) {
        this.controller = controller
    }

    override fun run() {
        menuMain()
    }

    private fun menuMain() {
        var operation: MatrixOperation
        while (true) {
            print(
                """Choose operation
            |1. Addition
            |2. Subtraction
            |3. Scalar multiplication
            |4. Multiplication
            |5. Transposition
            |0. Exit
            |""".trimMargin()
            )
            try {
                val option = readLine()!!.toInt()
                if (option == 0) break
                operation = MatrixOperation.values()[option - 1]
                println()
                val result = when (operation) {
                    ADDITION, SUBTRACTION, MULTIPLICATION -> {
                        println("Enter matrices:")
                        val a = readMatrixFromConsole()
                        val b = readMatrixFromConsole()
                        arrayOf(arrayOf(0, 1, 0)) // temp
                    }
                    SCALAR_MULTIPLICATION -> {
                        println("Enter matrix:")
                        val a = readMatrixFromConsole()
                        println("Enter scalar:")
                        val scalar = readLine()!!.trim().toInt()
                        arrayOf(arrayOf(0, 1, 0)) // temp
                    }
                    TRANSPOSITION -> {
                        println("Enter matrix:")
                        val a = readMatrixFromConsole()
                        arrayOf(arrayOf(0, 1, 0)) // temp
                    }
                }
                println()
                printMatrix(result)
                println()

            } catch (e: Exception) {
                println("\nIncorrect input, please try again\n")
                continue
            }
        }
    }

    private fun readMatrixFromConsole(): Array<Array<Int>> {
        val sizes = readLine()!!.trim().split(' ').map { value -> value.toInt() }
        if (sizes.size != 2) throw IllegalArgumentException()
        return Array(sizes[0]) {
            readLine()!!.trim().split(' ')
                .map { value -> value.toInt() }
                .toTypedArray()
        }
    }

    private fun printMatrix(matrix: Array<Array<Int>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                print(matrix[i][j])
                if (j != matrix[i].lastIndex) print(' ')
                else println()
            }
        }
    }
}
