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
        mainMenu@ while (true) {
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
                        val r: Array<Array<Int>>
                        try {
                            r = controller.process(readMatrixFromConsole(), readMatrixFromConsole(), operation)
                        } catch (e: IncompatibleMatricesException) {
                            println("\nError, incompatible matrices\n")
                            continue@mainMenu
                        }
                        r
                    }
                    SCALAR_MULTIPLICATION -> {
                        println("Enter matrix:")
                        val a = readMatrixFromConsole()
                        println("Enter scalar:")
                        val scalar = readLine()!!.trim().toInt()
                        throw NotImplementedError() // temp
                    }
                    TRANSPOSITION -> {
                        println("Enter matrix:")
                        val a = readMatrixFromConsole()
                        throw NotImplementedError() // temp
                    }
                }
                println()
                printMatrix(result)
                println()
            } catch (e: NotImplementedError) {
                println("\nOperation not implemented\n")
            } catch (e: NumberFormatException) {
                println("\nIncorrect input, please try again\n")
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
