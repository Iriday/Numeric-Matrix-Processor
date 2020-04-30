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
