package processor

interface ControllerInterface {
    fun run()
    fun process(a: Array<Array<Int>>, b: Array<Array<Int>>, operation: MatrixOperation): Array<Array<Int>>
}
