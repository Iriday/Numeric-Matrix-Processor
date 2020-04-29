package processor

import processor.MatrixOperation.*

class Model : ModelInterface {
    override fun process(a: Array<Array<Int>>, b: Array<Array<Int>>, operation: MatrixOperation): Array<Array<Int>> {
        return when (operation) {
            ADDITION -> {
                matrixAddition(a, b)
            }
            else -> throw NotImplementedError()
        }
    }
}

fun matrixAddition(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
    if (!isMatricesSizesEqual(a, b)) throw IncompatibleMatricesException()
    return a.mapIndexed { i, arr -> arr.mapIndexed { j, value -> value + b[i][j] }.toTypedArray() }.toTypedArray()
}

