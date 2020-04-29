package processor

class ViewConsole : ViewInterface {
    private lateinit var controller: Controller

    override fun initialize(controller: Controller) {
        this.controller = controller
    }

    override fun run() {
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
            |0. Exit
            |""".trimMargin()
            )
            try {
                val option = readLine()!!.trim()
                println()

                val result = when (option) {
                    "1", "2", "3" -> {
                        println("Enter matrices:")
                        val r: Array<Array<Int>>
                        try {
                            val a = readMatrixFromConsole()
                            val b = readMatrixFromConsole()
                            r = when (option) {
                                "1" -> controller.matrixAddition(a, b)
                                "2", "3" -> throw NotImplementedError()
                                else -> throw IllegalArgumentException("Something went wrong")
                            }
                        } catch (e: IncompatibleMatricesException) {
                            println("\nError, incompatible matrices\n")
                            continue@mainMenu
                        }
                        r
                    }
                    "4" -> {
                        println("Enter matrix:")
                        val a = readMatrixFromConsole()
                        println("Enter scalar:")
                        val scalar = readLine()!!.trim().toInt()
                        throw NotImplementedError() // temp
                    }
                    "5" -> {
                        println("Enter matrix:")
                        val a = readMatrixFromConsole()
                        throw NotImplementedError() // temp
                    }
                    "0" -> break@mainMenu
                    else -> {
                        println("Incorrect input, please try again\n")
                        continue@mainMenu
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
