package processor

import java.math.BigDecimal

fun isMatrixCorrect(matrix: Array<Array<BigDecimal>>): Boolean {
    if (matrix.isEmpty() || matrix[0].isEmpty()) return false
    val rows = matrix[0].size
    matrix.forEach { if (it.size != rows) return false }
    return true
}

fun isMatricesSizesEqual(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Boolean {
    if (!isMatrixCorrect(a) || !isMatrixCorrect(b)) throw IllegalArgumentException("Incorrect matrix")
    return a.size == b.size && a[0].size == b[0].size
}

fun isMatricesCompatibleForMultiplication(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Boolean {
    if (!isMatrixCorrect(a) || !isMatrixCorrect(b)) throw IllegalArgumentException("Incorrect matrix")
    return a[0].size == b.size
}

// matrix operations

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
    if (!isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")
    return matrix.map { row -> row.map { value -> value * scalar }.toTypedArray() }.toTypedArray()
}

fun matrixTransVerticalLine(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")
    val cols = matrix[0].size
    val newMatrix = Array(matrix.size) { Array(cols) { BigDecimal.ZERO } }

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            newMatrix[i][j] = matrix[i][cols - 1 - j]
        }
    }
    return newMatrix
}
