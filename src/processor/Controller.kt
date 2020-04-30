package processor

import java.math.BigDecimal

class Controller(private val model: ModelInterface, private val view: ViewInterface) : ControllerInterface {
    init {
        view.initialize(this)
        run()
    }

    override fun run() {
        view.run()
    }

    override fun matrixAddition(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Array<Array<BigDecimal>> {
        return model.matrixAddition(a, b)
    }

    override fun matrixScalarMultiplication(a: Array<Array<BigDecimal>>, scalar: BigDecimal): Array<Array<BigDecimal>> {
        return model.matrixScalarMultiplication(a, scalar)
    }
}
