package processor

fun isMatricesSizesEqual(a: Array<Array<Int>>, b: Array<Array<Int>>): Boolean {
    if (a.size != b.size) return false
    val firstRowSize = a[0].size
    a.forEachIndexed { i, row -> if (row.size != firstRowSize || b[i].size != firstRowSize) return false }
    return true
}
