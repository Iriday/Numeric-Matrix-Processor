package processor

class ViewConsole : ViewInterface {
    private lateinit var controller: Controller

    override fun initialize(controller: Controller) {
        this.controller = controller
    }

    override fun run() {

    }
}
