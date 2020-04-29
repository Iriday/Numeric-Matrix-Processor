package processor

class Controller(private val model: ModelInterface, private val view: ViewInterface) : ControllerInterface {
    init {
        view.initialize(this)
        run()
    }

    override fun run() {
        view.run()
    }

   override fun matrixAddition(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
        return model.matrixAddition(a, b)
    }
}
