package processor

class Controller(private val model: ModelInterface, private val view: ViewInterface) : ControllerInterface {
    init {
        view.initialize(this)
        run()
    }

    override fun run() {
        view.run()
    }

    override fun process(a: Array<Array<Int>>, b: Array<Array<Int>>, operation: MatrixOperation): Array<Array<Int>> {
        return model.process(a, b, operation)
    }
}
