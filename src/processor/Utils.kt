package processor

import java.math.BigDecimal

fun isMatricesSizesEqual(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Boolean {
    if (a.size != b.size) return false
    val firstRowSize = a[0].size
    a.forEachIndexed { i, row -> if (row.size != firstRowSize || b[i].size != firstRowSize) return false }
    return true
}

fun isMatricesCompatibleForMultiplication(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Boolean {
    if (!isMatrixCorrect(a) || !isMatrixCorrect(b)) throw IllegalArgumentException("Incorrect matrix")
    return a[0].size == b.size
}

fun isMatrixCorrect(matrix: Array<Array<BigDecimal>>): Boolean {
    if (matrix.isEmpty() || matrix[0].isEmpty()) return false
    val rows = matrix[0].size
    matrix.forEach { if (it.size != rows) return false }
    return true
}
