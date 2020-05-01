package processor

import java.math.BigDecimal

interface ModelInterface {
    fun matrixAddition(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>>

    fun matrixMultiplication(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>>

    fun matrixScalarMultiplication(a: Array<Array<BigDecimal>>, scalar: BigDecimal): Array<Array<BigDecimal>>

    fun matrixTransVerticalLine(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>>

    fun matrixTransHorizontalLine(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>>

    fun matrixTransMainDiagonal(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>>
}
