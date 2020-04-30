package processor

import java.math.BigDecimal

interface ControllerInterface {
    fun run()
    fun matrixAddition(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>>
    fun matrixScalarMultiplication(a: Array<Array<BigDecimal>>, scalar: BigDecimal): Array<Array<BigDecimal>>
}
