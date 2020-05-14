package processor

import java.math.BigDecimal
import java.math.RoundingMode

fun formatMatrix(matrix: Array<Array<BigDecimal>>, scale: Int): Array<Array<String>> {
    val scaledMatrix = matrix.map { row -> row.map { value -> value.setScale(scale, RoundingMode.HALF_EVEN).stripTrailingZeros()!! } }
    val newMatrix = scaledMatrix.map { row -> row.map { it.toString() } }

    val maxStrLenInRow = ArrayList<Int>()
    for (i in newMatrix[0].indices) {
        var max = 0
        for (j in newMatrix.indices) {
            max = kotlin.math.max(max, newMatrix[j][i].length)
        }
        maxStrLenInRow.add(max)
    }

    return newMatrix.map { row ->
        var i = -1
        row.map { value ->
            String.format("%${maxStrLenInRow[++i]}s", value)
        }.toTypedArray()
    }.toTypedArray()
}

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
    var i = -1
    return Array(matrix[0].size) {
        i++
        var j = -1
        Array(matrix.size) {
            j++
            matrix[j][i]
        }
    }
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
    var i = -1
    return Array(matrix.size) {
        i++
        var j = matrix[0].size
        Array(matrix[0].size) {
            j--
            matrix[i][j]
        }
    }
}

fun matrixTransHorizontalLine(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")
    var i = matrix.size
    return Array(matrix.size) {
        i--
        var j = -1
        Array(matrix[0].size) {
            j++
            matrix[i][j]
        }
    }
}

fun matrixDeterminant(matrix: Array<Array<BigDecimal>>): BigDecimal {
    if (matrix.size < 2 || matrix.size != matrix[0].size || !isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")
    return determinant(matrix)
}

fun matrixInversion(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (matrix.size < 2 || matrix.size != matrix[0].size || !isMatrixCorrect(matrix)) throw IllegalArgumentException("Incorrect matrix")

    val determinant = matrixDeterminant(matrix)
    if (determinant == BigDecimal.ZERO) return arrayOf() // if not invertible(singular) matrix, return empty array

    if (matrix.size == 2) return inversion2x2Matrix(matrix)

    // fill with cofactors
    var i = -1
    var newMatrix = Array(matrix.size) {
        i++
        var j = -1
        Array(matrix[0].size) {
            j++
            cofactor(determinant(subMatrix(matrix, i, j)), i, j)
        }
    }

    newMatrix = matrixTransMainDiagonal(newMatrix)

    return matrixScalarMultiplication(newMatrix, BigDecimal.ONE.divide(determinant, 5, RoundingMode.HALF_EVEN))
}

private fun inversion2x2Matrix(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    val newMatrix = arrayOf(arrayOf(matrix[1][1], -matrix[0][1]), arrayOf(-matrix[1][0], matrix[0][0]))
    return matrixScalarMultiplication(newMatrix, BigDecimal.ONE.divide(determinant2x2Matrix(matrix), 5, RoundingMode.HALF_EVEN))
}

private fun determinant2x2Matrix(matrix: Array<Array<BigDecimal>>): BigDecimal {
    return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
}

private fun cofactor(minor: BigDecimal, indexRow: Int, indexCol: Int): BigDecimal {
    return if ((indexRow + 1 + indexCol + 1) % 2 == 0) BigDecimal.ONE * minor else -BigDecimal.ONE * minor
}

// return new matrix with specified row and col not included
private fun subMatrix(matrix: Array<Array<BigDecimal>>, indexExcRow: Int, indexExcCol: Int): Array<Array<BigDecimal>> {
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

private fun determinant(matrix: Array<Array<BigDecimal>>): BigDecimal {
    if (matrix.size == 2) return determinant2x2Matrix(matrix)

    var sum = BigDecimal.ZERO
    val i = 0 // first row only
    matrix[i].forEachIndexed { j, matrixij ->
        sum += matrixij * cofactor(determinant(subMatrix(matrix, i, j)), i, j)
    }
    return sum
}
