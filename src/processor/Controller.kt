package processor

class Controller(private val model: ModelInterface, private val view: ViewInterface) : ControllerInterface {
    init {
        view.initialize(this)
        run()
    }

    override fun run() {
        view.run()
    }
}
