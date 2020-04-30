package processor

import java.math.BigDecimal


class Model : ModelInterface {
    override fun matrixAddition(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
        return processor.matrixAddition(a, b)
    }

    override fun matrixScalarMultiplication(a: Array<Array<BigDecimal>>, scalar: BigDecimal): Array<Array<BigDecimal>> {
        return processor.matrixScalarMultiplication(a, scalar)
    }
}

fun matrixAddition(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
    if (!isMatricesSizesEqual(a, b)) throw IncompatibleMatricesException()
    return a.mapIndexed { i, arr -> arr.mapIndexed { j, value -> value + b[i][j] }.toTypedArray() }.toTypedArray()
}

fun matrixScalarMultiplication(matrix: Array<Array<BigDecimal>>, scalar: BigDecimal): Array<Array<BigDecimal>> {
    return matrix.map { row -> row.map { value -> value * scalar }.toTypedArray() }.toTypedArray()
}
