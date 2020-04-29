package processor


class Model : ModelInterface {
    override fun matrixAddition(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
        return processor.matrixAddition(a, b)
    }
}

fun matrixAddition(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
    if (!isMatricesSizesEqual(a, b)) throw IncompatibleMatricesException()
    return a.mapIndexed { i, arr -> arr.mapIndexed { j, value -> value + b[i][j] }.toTypedArray() }.toTypedArray()
}

