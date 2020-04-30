package processor

import java.math.BigDecimal


class Model : ModelInterface {
    override fun matrixAddition(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
        return processor.matrixAddition(a, b)
    }

    override fun matrixMultiplication(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
        return processor.matrixMultiplication(a, b)
    }

    override fun matrixScalarMultiplication(a: Array<Array<BigDecimal>>, scalar: BigDecimal): Array<Array<BigDecimal>> {
        return processor.matrixScalarMultiplication(a, scalar)
    }
}

fun matrixAddition(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatricesSizesEqual(a, b)) throw IncompatibleMatricesException()
    return a.mapIndexed { i, arr -> arr.mapIndexed { j, value -> value + b[i][j] }.toTypedArray() }.toTypedArray()
}

fun matrixMultiplication(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatricesCompatibleForMultiplication(a, b)) throw IncompatibleMatricesException()
    val result = Array<Array<BigDecimal>>(a.size) { Array(b[0].size) { BigDecimal.ZERO } }
    var sumTemp = BigDecimal.ZERO

    for (i in a.indices) {
        for (j in b[0].indices) {
            for (k in b.indices) {
                sumTemp += a[i][k] * b[k][j]
            }
            result[i][j] = sumTemp
            sumTemp = BigDecimal.ZERO
        }
    }
    return result
}

fun matrixScalarMultiplication(matrix: Array<Array<BigDecimal>>, scalar: BigDecimal): Array<Array<BigDecimal>> {
    return matrix.map { row -> row.map { value -> value * scalar }.toTypedArray() }.toTypedArray()
}
