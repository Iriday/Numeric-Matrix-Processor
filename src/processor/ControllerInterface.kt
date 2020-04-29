package processor

interface ControllerInterface {
    fun run()
    fun matrixAddition(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>>
    fun matrixScalarMultiplication(a: Array<Array<Int>>, scalar: Int): Array<Array<Int>>
}
