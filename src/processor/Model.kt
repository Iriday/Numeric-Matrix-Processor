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

    override fun matrixTransVerticalLine(matrix: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
        return processor.matrixTransVerticalLine(matrix)
    }
}
