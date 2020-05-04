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

fun matrixSubtraction(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatricesSizesEqual(a, b)) throw IncompatibleMatricesException()
    return a.mapIndexed { i, arr -> arr.mapIndexed { j, value -> value - b[i][j] }.toTypedArray() }.toTypedArray()
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

fun matrixTransMainDiagonal(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")
    val newMatrix = Array(matrix[0].size) { Array(matrix.size) { BigDecimal.ZERO } }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            newMatrix[j][i] = matrix[i][j]
        }
    }
    return newMatrix
}

fun matrixTransSideDiagonal(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")
    val rows = matrix.size
    val cols = matrix[0].size
    val newMatrix = Array(cols) { Array(rows) { BigDecimal.ZERO } }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            newMatrix[cols - 1 - j][rows - 1 - i] = matrix[i][j]
        }
    }
    return newMatrix
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

fun matrixTransHorizontalLine(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")
    val rows = matrix.size
    val newMatrix = Array(rows) { Array(matrix[0].size) { BigDecimal.ZERO } }

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            newMatrix[i][j] = matrix[rows - 1 - i][j]
        }
    }
    return newMatrix
}

fun matrixDeterminant(matrix: Array<Array<BigDecimal>>): BigDecimal {
    if (matrix.size < 2 || matrix.size != matrix[0].size || !isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")

    fun determinant2x2Matrix(matrix: Array<Array<BigDecimal>>): BigDecimal {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    }

    fun cofactor(minor: BigDecimal, indexRow: Int, indexCol: Int): BigDecimal {
        return if ((indexRow + 1 + indexCol + 1) % 2 == 0) BigDecimal.ONE * minor else -BigDecimal.ONE * minor
    }

    // return new matrix with specified row and col not included
    fun subMatrix(matrix: Array<Array<BigDecimal>>, indexExcRow: Int, indexExcCol: Int): Array<Array<BigDecimal>> {
        val rows = matrix.size - 1
        val cols = matrix[0].size - 1

        var i = -1
        return Array(rows) {
            var j = -1
            ++i
            if (i == indexExcRow) i++

            Array(cols) {
                ++j
                if (j == indexExcCol) j++

                matrix[i][j]
            }
        }
    }

    fun determinant(matrix: Array<Array<BigDecimal>>): BigDecimal {
        if (matrix.size == 2) return determinant2x2Matrix(matrix)

        var sum = BigDecimal.ZERO
        val i = 0 // first row only
        matrix[i].forEachIndexed { j, matrixij ->
            sum += matrixij * cofactor(determinant(subMatrix(matrix, i, j)), i, j)
        }
        return sum
    }

    return determinant(matrix)
}
